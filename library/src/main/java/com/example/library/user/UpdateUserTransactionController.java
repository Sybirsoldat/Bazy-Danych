package com.example.library.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/transactions")
public class UpdateUserTransactionController {

    @Autowired
    private UpdateUserTransactionService updateUserTransactionService;

    @PutMapping("/update-user/{id}")
    @Operation(summary = "Update User", description = "Update the details of an existing user.")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID użytkownika", example = "1") @PathVariable Long id,
            @Parameter(description = "Szczegóły użytkownika") @RequestBody User userDetails) {
        User updatedUser = updateUserTransactionService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}