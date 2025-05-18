package com.example.spring_ai_example.assistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.ollama.OllamaContainer;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.io.IOException;


@SpringBootTest(properties = {
        "spring.ai.model.chat=ollama",
        "spring.ai.model.embedding=ollama",
        "spring.ai.model.audio.speech=ollama",
        "spring.ai.model.audio.transcription=ollama",
        "spring.ai.model.image=ollama",
        "spring.ai.model.moderation=ollama"
})
@Testcontainers
@AutoConfigureMockMvc
class AssistantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Container
    @ServiceConnection("postgres")
    private static final PostgreSQLContainer<?> pgVector = new PostgreSQLContainer<>("pgvector/pgvector:pg16")
            .withEnv("POSTGRES_DB", "mydatabase")
            .withEnv("POSTGRES_PASSWORD", "secret")
            .withEnv("POSTGRES_USER", "myuser")
            .withExposedPorts(5432);

    @Container
    @ServiceConnection("ollama")
    static OllamaContainer ollamaContainer = new OllamaContainer("ollama/ollama:0.5.7");

    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {
       ollamaContainer.execInContainer("ollama", "pull", "mistral");
       ollamaContainer.execInContainer("ollama", "pull", "llava");
       ollamaContainer.execInContainer("ollama", "pull", "llama3.2:1b");
       ollamaContainer.execInContainer("ollama", "pull", "mxbai-embed-large");
    }

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/chat").param("question", "Who is the best dog?")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Digby")));

    }
}