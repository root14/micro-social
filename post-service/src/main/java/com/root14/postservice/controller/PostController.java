package com.root14.postservice.controller;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/addPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPost(@RequestHeader("authenticated-user-id") String authenticatedUserId, @RequestPart(value = "dto", required = false) AddPostDto addPostDto, @RequestPart(value = "image", required = false) MultipartFile image) {
        return postService.addPost(addPostDto, authenticatedUserId, image);
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
