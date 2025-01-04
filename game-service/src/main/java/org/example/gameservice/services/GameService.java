package org.example.gameservice.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.gameservice.entities.GuessedWordEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
@RequiredArgsConstructor
public class GameService {
    private final GptService gptService;
    private final Map<Long, String> users_words = new HashMap<>();

    public String handleMessage(String message, long chat_id) {
        if (message.equals("/start")) {
            return start();
        }
        if (users_words.containsKey(chat_id) && message.equals("/stop")) {
            return stop(chat_id);
        }
        if (users_words.containsKey(chat_id)) {
            return handleGptRequest(message, chat_id);
        }
        if (!users_words.containsKey(chat_id) && message.equals("/play")) {
            return play(chat_id);
        }

        return "I don't understand you";
    }

    private String start() {
        return """
                It's a guess game, bot suggests you a topic and you should guess a word
                You can ask questions and bot answers yes/no
                Type /play to start game
                Type /stop if you give up
                """;
    }

    private String stop(long chat_id) {
        String word = users_words.get(chat_id);
        users_words.remove(chat_id);
        return "Your word is " + word;
    }

    private String play(long chat_id) {
        GuessedWordEntity entity = gptService.getChosenWord();
        if (entity.getError() != null) {
            return entity.getError();
        } else {
            users_words.put(chat_id, entity.getWord());
            return "Your topic is " + entity.getTopic();
        }
    }

    private String handleGptRequest(String message, long chat_id) {
        String response = gptService.guessWord(users_words.get(chat_id), message);
        if (response.equals("done")) {
            String word = users_words.get(chat_id);
            users_words.remove(chat_id);
            return "Congrats! Your word is " + word;
        }
        return response;
    }
}
