package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "code")
    String code;

    @Column(name = "chatId")
    Long chatId;
}