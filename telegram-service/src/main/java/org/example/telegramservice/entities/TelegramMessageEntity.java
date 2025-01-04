package org.example.telegramservice.entities;

import lombok.Data;
import lombok.ToString;

@Data
public class TelegramMessageEntity {
    String message;
    long chat_id;
}