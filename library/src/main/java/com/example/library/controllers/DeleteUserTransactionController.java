package com.example.library.controllers;

import com.example.library.services.DeleteUserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class DeleteUserTransactionController {

    @Autowired
    private DeleteUserTransactionService deleteUserTransactionService;

    /**
     * Deletes a user account completely.
     */
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        deleteUserTransactionService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
