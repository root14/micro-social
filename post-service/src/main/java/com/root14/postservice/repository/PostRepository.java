package com.root14.postservice.repository;

import com.root14.postservice.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {
    Post findPostByAuthorId(String title);

    Post findPostById(String title);
    //todo add full text search by tags or #####
}
