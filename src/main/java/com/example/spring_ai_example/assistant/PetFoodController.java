package com.example.spring_ai_example.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetFoodController {

    final ChatClient chatClient;

    public PetFoodController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("pet/food")
    PetWithFood petWithFoods(@RequestParam final String name) {
        return chatClient.prompt("Give me what you think the pets favorite food is. You can make it up. The pet name is %s".formatted(name))
                .call().entity(new ParameterizedTypeReference<>() {});
    }


    record PetWithFood(String petName, String breed, String favoriteFood)  {}
}
