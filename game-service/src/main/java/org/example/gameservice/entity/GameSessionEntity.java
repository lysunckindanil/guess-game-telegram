package org.example.gameservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GameSessionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "word")
    private String word;
}
