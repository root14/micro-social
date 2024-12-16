package com.root14.postvalidatorservice.repository;

import com.root14.postvalidatorservice.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {

}
