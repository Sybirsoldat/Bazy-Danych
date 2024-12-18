package com.example.library.controllers;

import com.example.library.models.OrdersBooks;
import com.example.library.services.OrdersBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders-books")
public class OrdersBooksController {

    @Autowired
    private OrdersBooksService ordersBooksService;

    @GetMapping
    public ResponseEntity<List<OrdersBooks>> getAllOrdersBooks() {
        return new ResponseEntity<>(ordersBooksService.getAllOrdersBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersBooks> getOrdersBooksById(@PathVariable Long id) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksById(id), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrdersBooks>> getOrdersBooksByOrderId(@PathVariable Long orderId) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<OrdersBooks>> getOrdersBooksByBookId(@PathVariable Long bookId) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksByBookId(bookId), HttpStatus.OK);
    }

    @GetMapping("/issued")
    public ResponseEntity<List<OrdersBooks>> getIssuedOrdersBooks(@RequestParam Boolean issued) {
        return new ResponseEntity<>(ordersBooksService.getIssuedOrdersBooks(issued), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrdersBooks> createOrdersBooks(@RequestBody OrdersBooks ordersBooks) {
        return new ResponseEntity<>(ordersBooksService.createOrdersBooks(ordersBooks), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersBooks> updateOrdersBooks(@PathVariable Long id, @RequestBody OrdersBooks ordersBooksDetails) {
        return new ResponseEntity<>(ordersBooksService.updateOrdersBooks(id, ordersBooksDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdersBooks(@PathVariable Long id) {
        ordersBooksService.deleteOrdersBooks(id);
        return ResponseEntity.noContent().build();
    }
}
