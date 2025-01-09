package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.User;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserTransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private UserService userService; // Wykorzystanie istniejącego serwisu

    /**
     * Dodanie nowego użytkownika do systemu z rejestracją aktywności.
     */
    @Transactional
    public User addUserTransaction(User user) {
        // Sprawdzenie unikalności (korzystamy z UserService)
        userService.validateUniqueFields(user);

        // Zapis użytkownika w bazie danych
        User savedUser = userRepository.save(user);

        // Rejestracja aktywności
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUser(savedUser); // Przypisz użytkownika
        activityLog.setAction("User created with ID: " + savedUser.getId());
        activityLog.setTimestamp(LocalDateTime.now());
        activityLogService.createActivityLog(activityLog);

        return savedUser;
    }
}
