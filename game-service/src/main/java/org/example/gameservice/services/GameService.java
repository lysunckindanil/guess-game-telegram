package org.example.gameservice.services;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    public String handleMessage(String message, long chat_id) {
        return message;
    }
}
