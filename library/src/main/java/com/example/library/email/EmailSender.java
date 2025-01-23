package com.example.library.email;

public interface EmailSender {
    void send(String to, String message, String subject);
}