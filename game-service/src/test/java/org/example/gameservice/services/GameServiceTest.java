package org.example.gameservice.services;

import org.example.gameservice.dto.GuessedWordDto;
import org.example.gameservice.repo.GameSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GameServiceTest {

    private final GameService gameService;
    private final GameSessionRepository gameSessionRepository;
    private final GptService gptService;

    private GuessedWordDto guessedWordDto;
    long chat_id = 1L;

    @Autowired
    GameServiceTest(GameSessionRepository gameSessionRepository) {
        gptService = Mockito.mock(GptService.class);
        this.gameSessionRepository = gameSessionRepository;
        this.gameService = new GameService(gptService, new GameSessionService(gameSessionRepository));

    }


    @BeforeEach
    public void setUp() {
        guessedWordDto = new GuessedWordDto();
        guessedWordDto.setWord("word");
        guessedWordDto.setTopic("topic");
        Mockito.when(gptService.getChosenWord()).thenReturn(guessedWordDto);
    }

    @Test
    void handleMessage_Play_AddToRepo() {
        gameService.handleMessage("/play", chat_id);
        assertThat(gameService.getWords_repo().size()).isEqualTo(1);
    }

    @Test
    void handleMessage_Play_ReturnsTopic() {
        assertEquals(gameService.handleMessage("/play", chat_id), "Your topic is " + guessedWordDto.getTopic());
    }

    @Test
    void handleMessage_PlayWithErrorResponse_ReturnError() {
        guessedWordDto.setError("error");
        gameService.handleMessage("/play", chat_id);
        assertEquals(gameService.handleMessage("/play", chat_id), "error");
    }

    @Test
    void handleMessage_PlayWithErrorResponse_DontAddToRepo() {
        guessedWordDto.setError("error");
        gameService.handleMessage("/play", chat_id);
        assertThat(gameService.getWords_repo().size()).isEqualTo(0);
    }


    @Test
    void handleMessage_Stop_DeletesFromRepo() {
        gameService.handleMessage("/play", chat_id);
        gameService.handleMessage("/stop", chat_id);
        assertThat(gameService.getWords_repo().size()).isEqualTo(0);
    }

    @Test
    void handleMessage_Stop_ReturnsGuessedWord() {
        gameService.handleMessage("/play", chat_id);
        assertEquals(gameService.handleMessage("/stop", chat_id), "Your word is " + guessedWordDto.getWord());
    }

    @Test
    void handleMessage_GuessDone_ReturnsThatWordGuessed() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(any(), any())).thenReturn("done");
        assertEquals(gameService.handleMessage("question_done", chat_id), "Congrats! Your word is " + guessedWordDto.getWord());
    }

    @Test
    void handleMessage_GuessDone_DeletesFromRepo() {
        gameService.handleMessage("/play", chat_id);
        Mockito.when(gptService.guessWord(any(), any())).thenReturn("done");
        gameService.handleMessage("question_done", chat_id);
        assertThat(gameService.getWords_repo().size()).isEqualTo(0);
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
        assertThat(gameService.getWords_repo().size()).isEqualTo(1);
    }
}