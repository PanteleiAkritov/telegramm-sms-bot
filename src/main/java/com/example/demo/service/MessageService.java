package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MessageService {
    final UserRepository userRepository;
    final SignatureLongPollingBot longPollingBot;

    public MessageService(UserRepository userRepository, SignatureLongPollingBot longPollingBot) {
        this.userRepository = userRepository;
        this.longPollingBot = longPollingBot;
    }

    public void sendVerificationCode(String code, String phoneNumber) {
        var user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user.getPhoneNumber().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        longPollingBot.sendTextMessage("Код для верификации: " + code, user.getChatId());
    }
}
