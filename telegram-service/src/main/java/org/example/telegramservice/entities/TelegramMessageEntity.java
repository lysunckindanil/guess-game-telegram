package org.example.telegramservice.entities;

import lombok.Data;

@Data
public class TelegramMessageEntity {
    String message;
    long chat_id;
}