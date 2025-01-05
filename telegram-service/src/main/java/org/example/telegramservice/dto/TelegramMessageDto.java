package org.example.telegramservice.dto;

import lombok.Data;

@Data
public class TelegramMessageDto {
    private String message;
    private long chat_id;
}