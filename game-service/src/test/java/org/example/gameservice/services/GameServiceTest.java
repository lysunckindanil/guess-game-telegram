package org.example.gameservice.services;

import org.example.gameservice.entities.GuessedWordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

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
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordEntity);
    }

    @Test
    void handleMessage_Play_AddToRepo() {
        gameService.handleMessage("/play", chat_id);
        assertThat(gameService.getWords_repo()).isNotEmpty();
    }

    @Test
    void handleMessage_Play_ReturnsTopic() {
        assertEquals(gameService.handleMessage("/play", chat_id), "Your topic is " + guessedWordEntity.getTopic());
    }

    @Test
    void handleMessage_PlayWithErrorResponse_ReturnError() {
        guessedWordEntity.setError("error");
        gameService.handleMessage("/play", chat_id);
        assertEquals(gameService.handleMessage("/play", chat_id), "error");
    }

    @Test
    void handleMessage_PlayWithErrorResponse_DontAddToRepo() {
        guessedWordEntity.setError("error");
        gameService.handleMessage("/play", chat_id);
        assertThat(gameService.getWords_repo()).isEmpty();
    }


    @Test
    void handleMessage_Stop_DeletesFromRepo() {
        gameService.handleMessage("/play", chat_id);
        gameService.handleMessage("/stop", chat_id);
        assertThat(gameService.getWords_repo()).isEmpty();
    }

    @Test
    void handleMessage_Stop_ReturnsGuessedWord() {
        gameService.handleMessage("/play", chat_id);
        assertEquals(gameService.handleMessage("/stop", chat_id), "Your word is " + guessedWordEntity.getWord());
    }

    @Test
    void handleMessage_GuessDone_ReturnsThatWordGuessed() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(any(), any())).thenReturn("done");
        assertEquals(gameService.handleMessage("question_done", chat_id), "Congrats! Your word is " + guessedWordEntity.getWord());
    }

    @Test
    void handleMessage_GuessDone_DeletesFromRepo() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(any(), any())).thenReturn("done");
        gameService.handleMessage("question_done", chat_id);
        assertThat(gameService.getWords_repo()).isEmpty();
    }

    @Test
    void handleMessage_GuessTry_ReturnsAnswerFromGpt() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(anyString(), anyString())).thenReturn("answer");
        assertEquals(gameService.handleMessage("question", chat_id), "answer");
    }

    @Test
    void handleMessage_GuessTry_WordNotDeletedRepo() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(anyString(), anyString())).thenReturn("answer");
        gameService.handleMessage("question", chat_id);
        assertThat(gameService.getWords_repo()).isNotEmpty();
    }
}