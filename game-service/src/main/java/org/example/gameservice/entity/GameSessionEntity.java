package org.example.gameservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
