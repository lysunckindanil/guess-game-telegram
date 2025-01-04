package org.example.gameservice.services;

import org.example.gameservice.entities.GuessedWordEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GptService gptService;

    @InjectMocks
    private GameService gameService;

    private final GuessedWordEntity guessedWordEntity = new GuessedWordEntity();
    long chat_id = 1L;

    @BeforeEach
    public void setUp() {
        guessedWordEntity.setWord("word");
        guessedWordEntity.setTopic("topic");
    }

    @Test
    void handleMessage_Play() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        Assertions.assertEquals(gameService.handleMessage("/play", chat_id), "Your topic is " + guessedWordEntity.getTopic());
        Assertions.assertEquals(gameService.getUsers_words().get(chat_id), guessedWordEntity.getWord());
        Assertions.assertEquals(1, gameService.getUsers_words().size());
    }

    @Test
    void handleMessage_PlayWithErrorResponse() {
        guessedWordEntity.setError("error");
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Assertions.assertEquals(gameService.handleMessage("/play", chat_id), "error");
        Assertions.assertEquals(0, gameService.getUsers_words().size());
        guessedWordEntity.setError(null);
    }

    @Test
    void handleMessage_Stop() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        gameService.handleMessage("/stop", chat_id);
        Assertions.assertNull(gameService.getUsers_words().get(chat_id));
        Assertions.assertEquals(0, gameService.getUsers_words().size());
    }

    @Test
    void handleMessage_GuessDone() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(guessedWordEntity.getWord(), "question_done")).thenReturn("done");
        Assertions.assertEquals(gameService.handleMessage("question_done", chat_id), "Congrats! Your word is " + guessedWordEntity.getWord());
        Assertions.assertNull(gameService.getUsers_words().get(chat_id));
        Assertions.assertEquals(0, gameService.getUsers_words().size());
    }

    @Test
    void handleMessage_GuessTry() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(guessedWordEntity.getWord(), "question_try")).thenReturn("answer");
        Assertions.assertEquals(gameService.handleMessage("question_try", chat_id), "answer");
        Assertions.assertEquals(gameService.getUsers_words().get(chat_id), guessedWordEntity.getWord());
        Assertions.assertEquals(1, gameService.getUsers_words().size());
    }
}