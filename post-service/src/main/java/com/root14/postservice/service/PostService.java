package com.root14.postservice.service;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.entity.Post;
import com.root14.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public ResponseEntity<?> addPost(AddPostDto addPostDto, String authenticatedUserId) {
        try {
            Post post = Post.builder().content(addPostDto.getContent()).authorId(authenticatedUserId).build();
            postRepository.save(post);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteByPostId(String id, boolean softDelete) {
        try {
            if (softDelete) {
                Optional<Post> post = postRepository.findById(id);
                if (post.isPresent()) {
                    post.get().setEnabled(false);
                    postRepository.save(post.get());
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                try {
                    postRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                } catch (Exception e) {
                    return ResponseEntity.notFound().build();
                }
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

    public ResponseEntity<?> updatePostByPostId(String postId, String content) {
        try {
            Optional<Post> post = postRepository.findByIdAndEnabled(postId, true);
            if (post.isPresent()) {
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
