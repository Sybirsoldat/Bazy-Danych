package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.models.OrdersBooks;
import com.example.library.services.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class OrderTransactionController {

    @Autowired
    private OrderTransactionService orderTransactionService;

    /**
     * Tworzenie zamówienia i aktualizacja stanu magazynowego.
     */
    @PostMapping("/create-order")
    public ResponseEntity<Orders> createOrderWithBooks(@RequestBody OrderRequest orderRequest) {
        Orders order = orderTransactionService.createOrderWithBooks(
                orderRequest.getOrder(),
                orderRequest.getBooks()
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}

class OrderRequest {
    private Orders order;
    private List<OrdersBooks> books;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<OrdersBooks> getBooks() {
        return books;
    }

    public void setBooks(List<OrdersBooks> books) {
        this.books = books;
    }
}
