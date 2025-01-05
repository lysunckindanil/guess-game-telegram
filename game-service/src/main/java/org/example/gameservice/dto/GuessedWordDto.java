package org.example.gameservice.dto;

import lombok.Data;

@Data
public class GuessedWordDto {
    String topic;
    String word;
    String error;
}
