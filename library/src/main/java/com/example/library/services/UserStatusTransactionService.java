package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.User;
import com.example.library.repositories.ActivityLogRepository;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatusTransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    /**
     * Uniwersalna transakcja zmiany statusu użytkownika.
     */
    @Transactional
    public User changeUserStatus(Long userId, int newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStatus() != newStatus) {
            // Zmień status użytkownika
            user.setStatus(newStatus);
            userRepository.save(user);

            // Dodaj wpis do dziennika aktywności
            ActivityLog log = new ActivityLog();
            log.setUser(user);
            log.setAction("Changed status of user ID: " + userId + " to " + (newStatus == 1 ? "active" : "inactive"));
            log.setTimestamp(java.time.LocalDateTime.now());
            activityLogRepository.save(log);
        }

        return user;
    }
}
