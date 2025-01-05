package org.example.gameservice.http;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TelegramMessageEntity {
    String message;
    long chat_id;
}
