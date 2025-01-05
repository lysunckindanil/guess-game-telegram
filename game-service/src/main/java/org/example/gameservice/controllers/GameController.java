package org.example.gameservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.gameservice.dto.TelegramMessageDto;
import org.example.gameservice.services.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/api/handle-message")
    public String handleMessage(@RequestBody TelegramMessageDto entity) {
        return gameService.handleMessage(entity.getMessage(), entity.getChat_id());
    }
}
