package com.makhtout.promptengai.controllers;

import com.makhtout.promptengai.outputs.Movie;
import com.makhtout.promptengai.outputs.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIAgentStructuredController {
    private ChatClient chatClient;

    public AIAgentStructuredController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("askAgent")
    public MovieList askLLM(String query) {
        String systemMessage = """
                Vous etes un specialiste dans le domaine du cinema
                Repond a la question de l'utilisateur a ce propos
                """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(query)
                .call()
                .entity(MovieList.class);
    }
}
