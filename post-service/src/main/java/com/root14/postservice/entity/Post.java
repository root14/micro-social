package com.root14.postservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "posts")
public class Post {
    @Id
    private String id;
    private String authorId;
    private String content;
    private Integer viewCount;
    private Integer favCount;
}
