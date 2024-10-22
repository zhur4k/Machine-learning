package com.machinelearning.bot;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


@Component
public class MyBot extends TelegramLongPollingBot {

        @Value("${bot.username}")
        private String botName;

        @Value("${bot.token}")
        private String botToken;

        @Override
        public String getBotUsername() {
            return botName;
        }

        @Override
        public String getBotToken() {
            return botToken;
        }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();

            if ("/start".equals(messageText)) {
                startBot(chatId, memberName);
            } else {
                decide(chatId, messageText);
            }
        }
    }

    private void startBot(long chatId, String userName) {
        sendMessage(chatId,"Привет, " + userName + "! Введи данные вот так:"+ "\n" +
                "Password" + "\n" +
                "1 a 1 1 1 1" + "\n" +
                "2 b 1 0 1 1" + "\n" +
                "3 a 1 1 1 1" + "\n" +
                "4 c 1 1 0 1" + "\n" +
                "5 a 1 0 1 1"
        );
    }
    private void decide(long chatId, String message){

    }
    public void sendMessage(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e){
        }
    }
}
