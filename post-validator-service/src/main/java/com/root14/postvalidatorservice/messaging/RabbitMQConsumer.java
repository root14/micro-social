package com.root14.postvalidatorservice.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "post-queue")
    public void receiveMessage(String message) {
        // Handle the received message here
        System.out.println("Received message: " + message);
    }
}