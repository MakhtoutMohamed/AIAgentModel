package com.makhtout.promptengai.controllers;

import com.makhtout.promptengai.outputs.CinModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultiModalController {
    private ChatClient chatClient;

    @Value("classpath:/images/cin1.jpg")
    private Resource image;

    @Value("classpath:/images/oppo.jpg")
    private Resource image2;

    public MultiModalController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/describe")
    public CinModel describeImage() {
        return chatClient.prompt()
                .system("Donne une description de l'image fournie")
                .user(u->u.text("Decrire cette image").media(MediaType.IMAGE_JPEG, image))
                .call()
                //.content();
                .entity(CinModel.class);
    }
/*    @GetMapping("/askImage")
    public String askImage(String question) {
        return chatClient.prompt()
                .system("Repond a la question de l'utilisateur a propos de l'image manuscrite fournie")
                .user(u->u.text(question).media(MediaType.IMAGE_JPEG, image2))
                .call()
                .content();
    }*/
    @PostMapping(value = "/askImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askImage(String question, @RequestParam MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return chatClient.prompt()
                .system("Repond a la question de l'utilisateur a propos de l'image manuscrite fournie")
                .user(u->u.text(question).media(MediaType.IMAGE_JPEG, new ByteArrayResource(bytes)))
                .call()
                .content();
    }
}