package com.root14.postservice.controller;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.dto.GetPostDto;
import com.root14.postservice.entity.Post;
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
    public ResponseEntity<?> getPost(@RequestBody GetPostDto getPostDto) {
        Post post = postService.getPost(getPostDto.getPostId());
        return ResponseEntity.ok().body(post);
    }

}
