package com.example.library.controllers;

import com.example.library.email.EmailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    private final EmailSender emailSender;

    public EmailTestController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/test-email")
    public String sendTestEmail(@RequestParam String to) {
        try {
            emailSender.send(to, "To jest testowa wiadomość.", "Test E-mail");
            return "E-mail wysłany pomyślnie do: " + to;
        } catch (Exception e) {
            return "Błąd podczas wysyłania e-maila: " + e.getMessage();
        }
    }
}
