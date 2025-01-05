package org.example.gameservice.http;

import lombok.Data;

@Data
public class GuessedWordEntity {
    String topic;
    String word;
    String error;
}
