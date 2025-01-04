package org.example.telegramservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telegramservice.entities.TelegramMessageEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RestTemplate restTemplate;
    private final String url = "http://game-service/api/handle-message";

    public String sendRequest(String message, long chat_id) {
        TelegramMessageEntity entity = new TelegramMessageEntity();
        entity.setMessage(message);
        entity.setChat_id(chat_id);
        return restTemplate.postForEntity(url, entity, String.class).getBody();
    }
}
