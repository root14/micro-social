package com.root14.postservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.dto.ImageDto;
import com.root14.postservice.entity.Post;
import com.root14.postservice.feign.ImageFeignClient;
import com.root14.postservice.messaging.RabbitMQProducer;
import com.root14.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final ImageFeignClient imageFeignClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public PostService(PostRepository postRepository, RabbitMQProducer rabbitMQProducer, ImageFeignClient imageFeignClient, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.rabbitMQProducer = rabbitMQProducer;
        this.imageFeignClient = imageFeignClient;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<?> addPost(AddPostDto addPostDto, String authenticatedUserId, MultipartFile image) {
        try {
            //post default save disabled
            String resultImageId = imageFeignClient.uploadImage(image);
            ImageDto imageDto = objectMapper.readValue(resultImageId, ImageDto.class);
            Post post = Post.builder().content(addPostDto
                            .getContent())
                    .authorId(authenticatedUserId)
                    .imageId(imageDto.getUuid())
                    .build();

            postRepository.save(post);
            rabbitMQProducer.sendMessage(post.getId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteByPostId(String id, boolean softDelete, String authenticatedUserId) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                //check, if user delete its own post with header authenticated user-id
                if (!post.get().getAuthorId().equals(authenticatedUserId)) {
                    return ResponseEntity.badRequest().build();
                } else {
                    if (!softDelete) {
                        postRepository.deleteById(id);
                        return ResponseEntity.ok().build();
                    }
                    post.get().setEnabled(false);
                    postRepository.save(post.get());
                    return ResponseEntity.ok().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    //todo do not return object raw
    public ResponseEntity<?> getPostByPostId(String postId) {
        try {
            Optional<Post> post = postRepository.findByIdAndEnabled(postId, true);

            if (post.isPresent()) {
                return ResponseEntity.ok().body(post.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    //todo do not return object raw
    public ResponseEntity<?> getPostsByAuthorId(String authorId) {
        try {
            Optional<List<Post>> optionalPostList = postRepository.findByAuthorIdAndEnabled(authorId, true);

            if (optionalPostList.isPresent() && !optionalPostList.get().isEmpty()) {
                return ResponseEntity.ok().body(optionalPostList.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    public ResponseEntity<?> updatePostByPostId(String postId, String content, String authenticatedUserId) {
        try {
            Optional<Post> post = postRepository.findByIdAndEnabled(postId, true);
            if (post.isPresent()) {
                if (!post.get().getAuthorId().equals(authenticatedUserId)) {
                    return ResponseEntity.status(403).build();
                }
                post.get().setContent(content);
                postRepository.save(post.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }
}
