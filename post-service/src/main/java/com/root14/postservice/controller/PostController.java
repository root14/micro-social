package com.root14.postservice.controller;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestBody AddPostDto addPostDto) {
        return postService.addPost(addPostDto, authenticatedUserId);
    }

    @GetMapping("/getPost")
    public ResponseEntity<?> getPost(@RequestParam String authorId) {
        return postService.getPostByPostId(authorId);
    }

    @DeleteMapping(value = "/deletePost")
    public ResponseEntity<?> deletePost(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestParam String authorId) {
        return postService.deleteByPostId(authorId, true, authenticatedUserId);
    }

    @GetMapping("/getPostsByAuthorId")
    public ResponseEntity<?> getPostByAuthorId(@RequestParam String authorId) {
        return postService.getPostsByAuthorId(authorId);
    }

    @PutMapping("/updatePostByPostId")
    public ResponseEntity<?> updatePostByPostId(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestParam String content, @RequestParam String postId) {
        return postService.updatePostByPostId(postId, content, authenticatedUserId);
    }
}
