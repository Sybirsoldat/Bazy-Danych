package com.example.library.controllers;

import com.example.library.services.DeleteUserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/transactions")
public class DeleteUserTransactionController {

    @Autowired
    private DeleteUserTransactionService deleteUserTransactionService;

    /**
     * Deletes a user account completely.
     */
    @DeleteMapping("/delete-user/{userId}")
    @Operation(summary = "Delete User", description = "Delete a user account completely.")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID użytkownika", example = "1") @PathVariable Long userId) {
        deleteUserTransactionService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
