package com.example.spring_ai_example.assistant;

import com.example.spring_ai_example.pet.PetRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(final ChatClient.Builder chatClientBuilder, final List<Advisor> advisors, final PetRepository petRepository) {
        return chatClientBuilder
                .defaultSystem("""
                        You are a chat bot for Pete's Pet shop. You help connect people with pets.
                        you should not try to sell a pet that already has an owner.
                        """)
                .defaultAdvisors(advisors)
                .defaultTools(new AdoptionTools(petRepository))
                .build();
    }

    @Bean
    PromptChatMemoryAdvisor promptChatMemoryAdvisor(final ChatMemoryRepository chatMemoryRepository) {
        final ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .build();
        return PromptChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    QuestionAnswerAdvisor questionAnswerAdvisor(final VectorStore vectorStore) {
        return new QuestionAnswerAdvisor(vectorStore);
    }

    @Bean
    CommandLineRunner commandLineRunner(final PetRepository petRepository, final VectorStore vectorStore) {
        return args -> petRepository.findAll().forEach(pet -> {
           var document = new Document("id: %s, name: %s, species: %s, breed: %s, dateOfBirth: %s, weight: %s, description: %s, ownerName: %s, ownerContact: %s"
                   .formatted(pet.id(), pet.name(), pet.species(), pet.breed(), pet.dateOfBirth(), pet.weight(), pet.description(), pet.ownerName(), pet.ownerContact()));
           vectorStore.add(List.of(document));
        });
    }
}
