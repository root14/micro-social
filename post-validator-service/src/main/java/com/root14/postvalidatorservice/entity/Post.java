package com.root14.postvalidatorservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

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
    private Boolean enabled;
    private List<String> hashtags;

}
