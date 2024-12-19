package com.example.library.controllers;

import com.example.library.models.Orders;
import com.example.library.services.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders API", description = "API do zarządzania zamówieniami w systemie bibliotecznym")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie zamówienia", description = "Zwraca listę wszystkich zamówień w systemie")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz zamówienie po ID", description = "Zwraca szczegóły zamówienia na podstawie jego ID")
    public ResponseEntity<Orders> getOrderById(
            @Parameter(description = "ID zamówienia", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(ordersService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Pobierz zamówienia użytkownika", description = "Zwraca listę zamówień na podstawie ID użytkownika")
    public ResponseEntity<List<Orders>> getOrdersByUserId(
            @Parameter(description = "ID użytkownika", example = "1")
            @PathVariable Long userId) {
        return new ResponseEntity<>(ordersService.getOrdersByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/active")
    @Operation(summary = "Pobierz aktywne zamówienia", description = "Zwraca listę wszystkich aktywnych zamówień")
    public ResponseEntity<List<Orders>> getActiveOrders() {
        return new ResponseEntity<>(ordersService.getActiveOrders(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nowe zamówienie", description = "Dodaje nowe zamówienie do systemu")
    public ResponseEntity<Orders> createOrder(
            @Parameter(description = "Dane nowego zamówienia")
            @RequestBody Orders order) {
        return new ResponseEntity<>(ordersService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj zamówienie", description = "Aktualizuje istniejące zamówienie na podstawie jego ID")
    public ResponseEntity<Orders> updateOrder(
            @Parameter(description = "ID zamówienia do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Zaktualizowane dane zamówienia")
            @RequestBody Orders orderDetails) {
        return new ResponseEntity<>(ordersService.updateOrder(id, orderDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń zamówienie", description = "Trwale usuwa zamówienie na podstawie ID")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID zamówienia do usunięcia", example = "1")
            @PathVariable Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}