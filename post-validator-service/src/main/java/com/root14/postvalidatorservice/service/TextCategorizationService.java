package com.root14.postvalidatorservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root14.postvalidatorservice.entity.Post;
import com.root14.postvalidatorservice.feign.UserFeignClient;
import com.root14.postvalidatorservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Service class responsible for categorizing posts using AI models.
 * It interacts with a third-party AI service to analyze the content of a post and determine its category.
 * The service fetches the category from the AI response and updates the post entity in the database.
 */
@Service
public class TextCategorizationService {

    private final RestTemplate restTemplate;
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    private final UserFeignClient userFeignClient;

    private final String API_KEY = System.getenv("google_studio_key");
    @Value("${google.studio.ai.api_url}")
    private String API_URL;

    /**
     * Constructor for TextCategorizationService.
     *
     * @param restTemplate   the RestTemplate used for making HTTP requests to the AI service
     * @param postRepository the PostRepository used for fetching and saving post entities
     * @param objectMapper   the ObjectMapper used for JSON parsing
     */
    @Autowired
    public TextCategorizationService(RestTemplate restTemplate, PostRepository postRepository, ObjectMapper objectMapper, UserFeignClient userFeignClient) {
        this.restTemplate = restTemplate;
        this.postRepository = postRepository;
        this.objectMapper = objectMapper;
        this.userFeignClient = userFeignClient;
    }

    /**
     * Handles the categorization of a post by fetching the post by its ID, analyzing its content with an AI model,
     * and updating its category and enabled status in the database.
     *
     * @param postId the ID of the post to categorize
     */
    public void handlePostCategorization(String postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()) {
            Post postEntity = post.get();
            String responseJson = analyzeContent(postEntity.getContent());
            String resultCategory = getCategoryFromJsonString(responseJson);

            postEntity.setCategory(resultCategory);
            postEntity.setEnabled(true);
            postRepository.save(postEntity);

            try {
                //inform interest user-service
                //make request to updateInterests
                String result = userFeignClient.updateInterest(postEntity.getAuthorId(), resultCategory);
                System.out.println(result);
            } catch (Exception e) {
                //todo implement logger system
                System.out.println(e);
            }
        }
    }

    /**
     * Parses the response JSON from the AI service to extract the category of the content.
     *
     * @param jsonString the response JSON string from the AI service
     * @return the category as a string, or null if an error occurs during parsing
     */
    private String getCategoryFromJsonString(String jsonString) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);

            JsonNode candidatesNode = rootNode.path("candidates");
            if (candidatesNode.isArray() && !candidatesNode.isEmpty()) {
                JsonNode contentNode = candidatesNode.get(0).path("content");
                JsonNode partsNode = contentNode.path("parts");
                if (partsNode.isArray() && !partsNode.isEmpty()) {
                    String categoryJsonString = partsNode.get(0).path("text").asText();

                    JsonNode categoryNode = objectMapper.readTree(categoryJsonString);
                    return categoryNode.path("category").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Analyzes the content of a post using an AI model to determine its category.
     * Sends a request to a third-party AI service and returns the response JSON.
     *
     * @param text the text content of the post to analyze
     * @return the response JSON as a string
     */
    private String analyzeContent(String text) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

        String jsonBody = String.format("{ \"contents\": [{ \"role\": \"user\", \"parts\": [{ \"text\": \"%s\" }] }] , " + "\"generationConfig\": { \"temperature\": 0, \"topK\": 40, \"topP\": 0.95, \"maxOutputTokens\": 8192, " + "\"responseMimeType\": \"application/json\", \"responseSchema\": { \"type\": \"object\", \"properties\": { \"category\": { \"type\": \"string\" } } } } }", text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }
}
