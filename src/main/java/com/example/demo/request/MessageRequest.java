package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {
    String phoneNumber;
    String text;
}
