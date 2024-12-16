package com.root14.postvalidatorservice.service;

import com.root14.postvalidatorservice.entity.Post;
import com.root14.postvalidatorservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HashtagsService {

    private final PostRepository postRepository;
    private final AnalyzeService analyzeService;

    @Autowired
    public HashtagsService(PostRepository postRepository, AnalyzeService analyzeService) {
        this.postRepository = postRepository;
        this.analyzeService = analyzeService;
    }

    public void handlePost(String postId) {
        try {
            Optional<Post> post = postRepository.findById(postId);

            if (post.isPresent()) {
                Post p = post.get();
                List<String> hashtags = analyzeService.extractHashtags(p.getContent());
                p.setHashtags(hashtags);
                p.setEnabled(true);
                postRepository.save(p);
            }

        } catch (Exception e) {
            //todo implement logger and re-queue system for exceptions
            throw e;
        }
    }


}
