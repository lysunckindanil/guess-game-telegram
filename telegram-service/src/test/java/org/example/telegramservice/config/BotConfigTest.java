package org.example.telegramservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BotConfigTest {
    private final BotConfig botConfig;

    @Autowired
    BotConfigTest(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Test
    public void testBotConfig_IsNotNull() {
        Assertions.assertNotNull(botConfig);
    }
}