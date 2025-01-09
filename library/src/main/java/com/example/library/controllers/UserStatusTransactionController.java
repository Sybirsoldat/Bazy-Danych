package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.services.UserStatusTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class UserStatusTransactionController {

    @Autowired
    private UserStatusTransactionService userStatusTransactionService;

    /**
     * Zmiana statusu użytkownika.
     */
    @PutMapping("/change-status/{userId}/{newStatus}")
    public ResponseEntity<User> changeUserStatus(@PathVariable Long userId,
                                                 @PathVariable int newStatus) {
        User updatedUser = userStatusTransactionService.changeUserStatus(userId, newStatus);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
