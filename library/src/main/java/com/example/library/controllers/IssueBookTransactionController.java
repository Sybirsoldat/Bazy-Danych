package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.IssueBookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class IssueBookTransactionController {

    @Autowired
    private IssueBookTransactionService issueBookTransactionService;

    /**
     * Wydanie książki na podstawie zamówienia.
     */
    @PutMapping("/issue-book/{orderId}/{bookId}")
    public ResponseEntity<Orders> issueBook(@PathVariable Long orderId,
                                            @PathVariable Long bookId ) {
        Orders order = issueBookTransactionService.issueBooks(orderId, bookId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}