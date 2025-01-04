package org.example.gameservice.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TelegramMessageEntity {
    String message;
    long chat_id;
}
