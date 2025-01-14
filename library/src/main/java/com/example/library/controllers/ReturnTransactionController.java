package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.ReturnTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/transactions")
public class ReturnTransactionController {

    @Autowired
    private ReturnTransactionService returnTransactionService;

    /**
     * Zwrot książki dla zamówienia.
     */
    @PutMapping("/return-book/{orderId}/{bookId}")
    @Operation(summary = "Return Book", description = "Return a book for a specific order.")
    public ResponseEntity<Orders> returnBooks(
            @Parameter(description = "ID zamówienia", example = "1") @PathVariable Long orderId,
            @Parameter(description = "ID książki", example = "10") @PathVariable Long bookId) {
        Orders order = returnTransactionService.returnBooks(orderId, bookId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}