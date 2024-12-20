package com.root14.postvalidatorservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.root14.postvalidatorservice.service.TextCategorizationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final TextCategorizationService textCategorizationService;

    @Autowired
    public RabbitMQConsumer(TextCategorizationService textCategorizationService) {
        this.textCategorizationService = textCategorizationService;
    }


    /**
     * Analyzes and updates the post with the given postId.
     * <p>
     * This method performs an analysis on the specified post identified by the {@code postId}.
     * After the analysis, it updates the post's details in the database. The exact analysis logic
     * and the update process depend on the post's current state and the intended changes.
     *
     * @param postId The unique identifier of the post to be analyzed and updated.
     *               This ID should refer to an existing post in the system.
     * @throws Exception if the post with the specified {@code postId} does not exist.
     * @throws Exception if an error occurs while updating the post.
     */
    @RabbitListener(queues = "post-queue")
    public void consumePost(String postId) throws JsonProcessingException {
        textCategorizationService.handlePostCategorization(postId);
    }
}