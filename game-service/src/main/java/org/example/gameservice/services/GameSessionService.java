package org.example.gameservice.services;

import lombok.RequiredArgsConstructor;
import org.example.gameservice.entity.GameSessionEntity;
import org.example.gameservice.repo.GameSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GameSessionService implements Map<Long, String> {
    private final GameSessionRepository gameSessionRepository;


    @Override
    public int size() {
        return (int) gameSessionRepository.count();
    }

    @Override
    public boolean isEmpty() {
        return (int) gameSessionRepository.count() == 0;
    }

    @Override
    public boolean containsKey(Object chat_id) {
        if (chat_id instanceof Long id) {
            return gameSessionRepository.existsByChatId(id);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean containsValue(Object word) {
        if (word instanceof String id) {
            return gameSessionRepository.existsByWord(id);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String get(Object chat_id) {
        if (chat_id instanceof Long id) {
            return gameSessionRepository.getByChatId(id).getWord();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String put(Long chat_id, String word) {
        GameSessionEntity gs = new GameSessionEntity();
        gs.setChatId(chat_id);
        gs.setWord(word);
        gameSessionRepository.save(gs);
        return word;
    }

    @Transactional
    @Override
    public String remove(Object chat_id) {
        if (chat_id instanceof Long id) {
            String word = gameSessionRepository.getByChatId(id).getWord();
            gameSessionRepository.deleteByChatId(id);
            return word;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void putAll(Map<? extends Long, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        gameSessionRepository.deleteAll();
    }

    @Override
    public Set<Long> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<Long, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
