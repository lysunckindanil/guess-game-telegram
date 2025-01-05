package org.example.gameservice.services;

import lombok.RequiredArgsConstructor;
import org.example.gameservice.dto.GuessedWordDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GptService {
    public final RestTemplate restTemplate;

    private final String guess_word_url = "http://gpt-service/api/guess-word?word=%s&question=%s";
    private final String choose_topic_url = "http://gpt-service/api/topics/choose";

    public GuessedWordDto getChosenWord() {
        return restTemplate.getForObject(choose_topic_url, GuessedWordDto.class);
    }

    public String guessWord(String word, String question) {
        return restTemplate.getForObject(String.format(guess_word_url, word, question), String.class);
    }

}
