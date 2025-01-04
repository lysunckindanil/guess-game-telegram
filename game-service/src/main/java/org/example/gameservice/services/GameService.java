package org.example.gameservice.services;

import lombok.RequiredArgsConstructor;
import org.example.gameservice.entities.GuessedWordEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GptService gptService;
    Map<Long, String> users_words = new HashMap<>();

    public String handleMessage(String message, long chat_id) {
        if (message.equals("/start")) {
            return """
                    It's a guess game, bot suggests you a topic and you should guess a word
                    You can ask questions and bot answers yes/no
                    Type /play to start game
                    Type /stop if you give up
                    """;
        }
        if (users_words.containsKey(chat_id)) {
            if (message.equals("/stop")) {
                String word = users_words.get(chat_id);
                users_words.remove(chat_id);
                return "Your word is " + word;
            } else {
                String response = gptService.guessWord(users_words.get(chat_id), message);
                if (response.equals("done")) {
                    String word = users_words.get(chat_id);
                    users_words.remove(chat_id);
                    return "Congrats! Your word is " + word;
                }
                return response;
            }
        } else {
            if (message.equals("/play")) {
                GuessedWordEntity entity = gptService.getChosenWord();
                users_words.put(chat_id, entity.getWord());
                return "Your topic is " + entity.getTopic();
            }
        }
        return "I don't understand you";
    }
}
