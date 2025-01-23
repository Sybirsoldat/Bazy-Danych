package com.example.library.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class UpdateUserTransactionController {

    @Autowired
    private UpdateUserTransactionService updateUserTransactionService;

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails) {
        User updatedUser = updateUserTransactionService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}
