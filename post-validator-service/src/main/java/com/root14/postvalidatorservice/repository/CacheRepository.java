package com.root14.postvalidatorservice.repository;

import com.root14.postvalidatorservice.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends CrudRepository<String, Long> {
}
