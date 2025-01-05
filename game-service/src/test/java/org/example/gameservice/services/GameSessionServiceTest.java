package org.example.gameservice.services;

import org.example.gameservice.entity.GameSessionEntity;
import org.example.gameservice.repo.GameSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GameSessionServiceTest {

    private final GameSessionRepository gameSessionRepository;
    private final GameSessionService gameSessionService;
    private final GameSessionEntity entity;

    @Autowired
    GameSessionServiceTest(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
        gameSessionService = new GameSessionService(gameSessionRepository);
        entity = GameSessionEntity.builder().chatId(1L).word("word").build();
    }


    @Test
    void size() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertThat(gameSessionService.size()).isEqualTo(1);
    }

    @Test
    void isEmpty() {
        assertThat(gameSessionService.size()).isEqualTo(0);
    }

    @Test
    void containsKey_True() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertTrue(gameSessionService.containsKey(entity.getChatId()));
    }

    @Test
    void containsKey_False() {
        gameSessionService.put(anyLong(), anyString());
        assertFalse(gameSessionService.containsKey(entity.getChatId()));
    }

    @Test
    void containsValue_True() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertTrue(gameSessionService.containsValue(entity.getWord()));
    }

    @Test
    void containsValue_False() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertFalse(gameSessionService.containsValue(anyString()));
    }

    @Test
    void get() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertEquals(gameSessionService.get(entity.getChatId()), entity.getWord());
    }

    @Test
    void put() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        assertEquals(gameSessionRepository.getByChatId(entity.getChatId()).getWord(), entity.getWord());
    }

    @Test
    void remove() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        gameSessionService.remove(entity.getChatId());
        assertThat(gameSessionService.size()).isEqualTo(0);
    }

    @Test
    void clear() {
        gameSessionService.put(entity.getChatId(), entity.getWord());
        gameSessionService.put(entity.getChatId(), entity.getWord());
        gameSessionService.clear();
        assertThat(gameSessionService.size()).isEqualTo(0);
    }
}