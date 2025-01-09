package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.services.UserTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "User Transactions", description = "API for user-related transactions")
public class UserTransactionController {

    @Autowired
    private UserTransactionService userTransactionService;

    /**
     * Dodanie nowego użytkownika do systemu.
     */
    @PostMapping("/add-user")
    @Operation(summary = "Add a new user", description = "Adds a new user to the system and logs the activity.")
    public ResponseEntity<User> addUser(
            @Parameter(description = "Details of the new user")
            @RequestBody User user) {
        User createdUser = userTransactionService.addUserTransaction(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
