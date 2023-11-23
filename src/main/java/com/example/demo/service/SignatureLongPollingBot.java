package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class SignatureLongPollingBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    String botUsername;
    final UserRepository userRepository;

    public SignatureLongPollingBot(@Value("${bot.token}") String botToken, UserRepository userRepository) {
        super(botToken);
        this.userRepository = userRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();

            if (message.hasText()) {
                String text = message.getText();
                if ("/start".equals(text)) {
                    sendStartMessage(chatId);
                }
            } else if (message.hasContact()) {
                Contact contact = message.getContact();
                String phoneNumber = contact.getPhoneNumber();
                saveUser(chatId, phoneNumber);
//                sendVerificationCode();
            }
        }
    }

    public void sendStartMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Нажмите на кнопку, чтобы отправить свой номер телефона.");

        KeyboardButton requestPhoneButton = new KeyboardButton();
        requestPhoneButton.setText("Отправить номер телефона");
        requestPhoneButton.setRequestContact(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(requestPhoneButton);

        List<KeyboardRow> rowList = new ArrayList<>();
        rowList.add(keyboardRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rowList);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(Long chatId, String phoneNumber) {
        User user = new User();
        user.setChatId(chatId);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void sendTextMessage(String text, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}