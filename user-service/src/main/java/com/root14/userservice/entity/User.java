package com.root14.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private String username;
    private Boolean enabled;
    private List<String> postIds;
}
