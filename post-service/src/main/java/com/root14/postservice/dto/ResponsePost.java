package com.root14.postservice.dto;

import lombok.Data;

@Data
public class ResponsePost {
    private String postId;
    private String content;
    private String authorId;
}
