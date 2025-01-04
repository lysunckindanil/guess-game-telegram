package org.example.telegramservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RestTemplate restTemplate;
    private final String url = "https://game-service/api/handle-message";

    public String sendRequest(String message) {
//        return restTemplate.getForObject(url, String.class, message);
        return "message: " + message + "\n";
    }
}
