package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.AddBookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/transactions")
public class AddBookTransactionController {

    @Autowired
    private AddBookTransactionService addBookTransactionService;

    @PostMapping("/add-book")
    @Operation(summary = "Add Book", description = "Add a new book to the system.")
    public ResponseEntity<?> addBook(
            @Parameter(description = "Szczegóły książki") @RequestBody Book bookDetails) {
        try {
            Book addedBook = addBookTransactionService.addNewBook(bookDetails);
            return ResponseEntity.ok(addedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
