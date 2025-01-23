package com.example.library.email;

import com.example.library.controllers.ActivityLogController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.library.models.ActivityLog;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;
    ActivityLogController activityLogService;

    @Override
    @Async
    public void send(String to, String message, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, "utf-8"
            );
            helper.setText(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("autobus2003@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log activity
            ActivityLog log = new ActivityLog();
            log.setAction("Email sender error");
            log.setTimestamp(LocalDateTime.now());
            activityLogService.createActivityLog(log);
            throw new IllegalStateException("Nie udało się wysłać emaila");
        }
    }
}