package com.root14.postservice.repository;

import com.root14.postservice.entity.Post;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {
    Optional<List<Post>> findByAuthorIdAndEnabled(String authorId, Boolean enabled);

    Optional<Post> findByIdAndEnabled(String title, Boolean enabled);

    //todo add full text search by tags or #####
}
