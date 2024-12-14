package com.root14.postservice.repository;

import com.root14.postservice.entity.Post;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {
    Optional<Post> findPostByAuthorIdAndEnabled(String authorId, Boolean enabled);

    Optional<List<Post>> findByAuthorIdAndEnabled(String authorId, Boolean enabled);

    Optional<Post> findByIdAndEnabled(String title, Boolean enabled);

    List<Post> getPostsByEnabled(Boolean enabled);

    Post getPostByEnabled(Boolean enabled);
    //todo add full text search by tags or #####
}
