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

    private GuessedWordEntity guessedWordEntity;
    long chat_id = 1L;

    @BeforeEach
    public void setUp() {
        guessedWordEntity = new GuessedWordEntity();
        guessedWordEntity.setWord("word");
        guessedWordEntity.setTopic("topic");
    }

    @Test
    void handleMessage_Play_ReturnsGuessedWordAndAddToRepo() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        Assertions.assertEquals(gameService.handleMessage("/play", chat_id), "Your topic is " + guessedWordEntity.getTopic());
        Assertions.assertEquals(gameService.getWords_repo().get(chat_id), guessedWordEntity.getWord());
        Assertions.assertEquals(1, gameService.getWords_repo().size());
    }

    @Test
    void handleMessage_PlayWithErrorResponse_ReturnErrorAndDontAddToRepo() {
        guessedWordEntity.setError("error");
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Assertions.assertEquals(gameService.handleMessage("/play", chat_id), "error");
        Assertions.assertEquals(0, gameService.getWords_repo().size());
    }

    @Test
    void handleMessage_Stop_DeletesChatIdFromRepoAndReturnsGuessedWord() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Assertions.assertEquals(gameService.handleMessage("/stop", chat_id), "Your word is " + guessedWordEntity.getWord());
        Assertions.assertNull(gameService.getWords_repo().get(chat_id));
        Assertions.assertEquals(0, gameService.getWords_repo().size());
    }

    @Test
    void handleMessage_GuessDone_ReturnsThatWordGuessedAndDeletesFromRepo() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(guessedWordEntity.getWord(), "question_done")).thenReturn("done");
        Assertions.assertEquals(gameService.handleMessage("question_done", chat_id), "Congrats! Your word is " + guessedWordEntity.getWord());
        Assertions.assertNull(gameService.getWords_repo().get(chat_id));
        Assertions.assertEquals(0, gameService.getWords_repo().size());
    }

    @Test
    void handleMessage_GuessTry_ReturnsAnswerFromGpt() {
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(guessedWordEntity.getWord(), "question_try")).thenReturn("answer");
        Assertions.assertEquals(gameService.handleMessage("question_try", chat_id), "answer");
        Assertions.assertEquals(gameService.getWords_repo().get(chat_id), guessedWordEntity.getWord());
        Assertions.assertEquals(1, gameService.getWords_repo().size());
    }
}