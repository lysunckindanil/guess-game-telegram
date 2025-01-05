package org.example.gameservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TelegramMessageDto {
    String message;
    long chat_id;
}
