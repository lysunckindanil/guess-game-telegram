package org.example.telegramservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;


@Component
@Slf4j
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final GameService gameService;

    public TelegramBot(GameService gameService) throws TelegramApiException {
        this.gameService = gameService;
        telegramClient = new OkHttpTelegramClient(getBotToken());
        BotCommand command1 = BotCommand.builder().command("/play").description("to start playing").build();
        BotCommand command2 = BotCommand.builder().command("/stop").description("to see the guessed word").build();
        List<BotCommand> LIST_OF_COMMANDS = List.of(command1, command2);
        telegramClient.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
    }


    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            long chat_id = update.getMessage().getChatId();
            String message_text = gameService.sendRequest(update.getMessage().getText(), chat_id);

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException ignored) {
            }
        }
    }

    @Override
    public String getBotToken() {
        return "7878222090:AAEND1MXpKLpFwOiJQ1hukkMcaW4PsSR3DE";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
