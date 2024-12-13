package com.root14.postservice.service;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.entity.Post;
import com.root14.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public ResponseEntity<?> addPost(AddPostDto addPostDto, String authenticatedUserId) {
        try {
            Post post = Post.builder()
                    .content(addPostDto.getContent())
                    .authorId(authenticatedUserId)
                    .build();
            postRepository.save(post);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong." + e.getMessage());
        }
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public Post getPost(String id) {
        return postRepository.findById(id).orElseThrow();
    }


}
