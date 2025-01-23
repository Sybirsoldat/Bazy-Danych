package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.IssueBookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/transactions")
public class IssueBookTransactionController {

    @Autowired
    private IssueBookTransactionService issueBookTransactionService;

    /**
     * Wydanie książki na podstawie zamówienia.
     */
    @PutMapping("/issue-book/{orderId}/{bookId}")
    @Operation(summary = "Issue Book", description = "Issue a book based on an order.")
    public ResponseEntity<Orders> issueBook(
            @Parameter(description = "ID zamówienia", example = "1") @PathVariable Long orderId,
            @Parameter(description = "ID książki", example = "10") @PathVariable Long bookId) {
        Orders order = issueBookTransactionService.issueBooks(orderId, bookId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}