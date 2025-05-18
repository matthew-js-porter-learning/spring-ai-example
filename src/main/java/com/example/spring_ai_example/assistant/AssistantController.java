package com.example.spring_ai_example.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AssistantController {

    private final ChatClient chatClient;

    AssistantController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("chat")
    String chat(@RequestParam String question) {
        return chatClient.prompt().user(question).call().content();
    }

}
