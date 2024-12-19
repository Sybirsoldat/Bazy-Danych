package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.ReturnTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class ReturnTransactionController {

    @Autowired
    private ReturnTransactionService returnTransactionService;

    /**
     * Zwrot książki dla zamówienia
     */
    @PutMapping("/return-book/{orderId}/{bookId}/{quantity}")
    public ResponseEntity<Orders> returnBooks(@PathVariable Long orderId,
                                              @PathVariable Long bookId,
                                              @PathVariable int quantity) {
        Orders order = returnTransactionService.returnBooks(orderId, bookId, quantity);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}