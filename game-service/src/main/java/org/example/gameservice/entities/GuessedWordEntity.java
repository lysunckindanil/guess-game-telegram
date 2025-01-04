package org.example.gameservice.entities;

import lombok.Data;

@Data
public class GuessedWordEntity {
    String topic;
    String word;
    String error;
}
