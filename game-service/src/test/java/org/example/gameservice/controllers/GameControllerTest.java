package org.example.gameservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gameservice.http.TelegramMessageEntity;
import org.example.gameservice.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = {GameController.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private GameService gameService;

    private TelegramMessageEntity telegramMessageEntity;


    @BeforeEach
    public void setUp() {
        telegramMessageEntity = new TelegramMessageEntity();
        telegramMessageEntity.setChat_id(1L);
        telegramMessageEntity.setMessage("message");
    }

    @Test
    public void handleMessage_ReturnSuccess() throws Exception {
        when(gameService.handleMessage(anyString(), anyLong())).thenReturn("string");
        ResultActions response = mockMvc.perform(post("/api/handle-message").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(telegramMessageEntity)));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}