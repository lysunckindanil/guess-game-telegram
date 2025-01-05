package org.example.telegramservice.entities;

import lombok.Data;

@Data
public class TelegramMessageEntity {
    private String message;
    private long chat_id;
}