package com.example.demo.controller;

import com.example.demo.service.MessageService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public void sendCode(@RequestParam String phoneNumber, @RequestParam String code) {
        messageService.sendVerificationCode(code, phoneNumber);
    }
}