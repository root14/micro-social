package com.root14.postservice.service;

import com.root14.postservice.dto.AddPostDto;
import com.root14.postservice.entity.Post;
import com.root14.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void addPost(AddPostDto addPostDto) {
        Post post = Post.builder()
                .content(addPostDto.getContent())
                .authorId(addPostDto.getAuthorId())
                .build();
        postRepository.save(post);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public Post getPost(String id) {
        return postRepository.findById(id).orElseThrow();
    }


}
