package org.example.gameservice.repo;

import org.example.gameservice.entity.GameSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSessionEntity, Long> {
    boolean existsByWord(String word);

    boolean existsByChatId(Long chatId);

    GameSessionEntity getByChatId(Long chatId);
}
