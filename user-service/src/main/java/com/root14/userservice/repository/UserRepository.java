package com.root14.userservice.repository;

import com.root14.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User getUsersByUsername(String username);

    Optional<User> findByUsername(String username);
}
