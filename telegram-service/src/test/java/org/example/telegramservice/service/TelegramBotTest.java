package org.example.telegramservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TelegramBotTest {

    private final TelegramBot bot;

    @Autowired
    TelegramBotTest(TelegramBot bot) {
        this.bot = bot;
    }

    @Test
    void getBotToken_ReturnNotNull() {
        Assertions.assertNotNull(bot.getBotToken());
    }
}