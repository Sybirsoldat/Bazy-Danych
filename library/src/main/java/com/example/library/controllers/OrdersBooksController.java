package com.example.library.controllers;

import com.example.library.models.OrdersBooks;
import com.example.library.services.OrdersBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders-books")
@Tag(name = "OrdersBooks API", description = "API do zarządzania powiązaniami książek z zamówieniami")
public class OrdersBooksController {

    @Autowired
    private OrdersBooksService ordersBooksService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie powiązania książek z zamówieniami", description = "Zwraca listę wszystkich powiązań książek z zamówieniami")
    public ResponseEntity<List<OrdersBooks>> getAllOrdersBooks() {
        return new ResponseEntity<>(ordersBooksService.getAllOrdersBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz powiązanie książki z zamówieniem po ID", description = "Zwraca szczegóły powiązania książki z zamówieniem na podstawie jego unikalnego ID")
    public ResponseEntity<OrdersBooks> getOrdersBooksById(
            @Parameter(description = "ID powiązania książki z zamówieniem", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksById(id), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Pobierz powiązania dla zamówienia", description = "Zwraca listę książek powiązanych z podanym ID zamówienia")
    public ResponseEntity<List<OrdersBooks>> getOrdersBooksByOrderId(
            @Parameter(description = "ID zamówienia", example = "1")
            @PathVariable Long orderId) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Pobierz zamówienia dla książki", description = "Zwraca listę zamówień, które zawierają podaną książkę")
    public ResponseEntity<List<OrdersBooks>> getOrdersBooksByBookId(
            @Parameter(description = "ID książki", example = "1")
            @PathVariable Long bookId) {
        return new ResponseEntity<>(ordersBooksService.getOrdersBooksByBookId(bookId), HttpStatus.OK);
    }

    @GetMapping("/issued")
    @Operation(summary = "Pobierz wydane książki", description = "Zwraca listę książek, które zostały wydane lub nie, w zależności od parametru")
    public ResponseEntity<List<OrdersBooks>> getIssuedOrdersBooks(
            @Parameter(description = "Czy książka została wydana?", example = "true")
            @RequestParam Boolean issued) {
        return new ResponseEntity<>(ordersBooksService.getIssuedOrdersBooks(issued), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nowe powiązanie książki z zamówieniem", description = "Tworzy nowe powiązanie między książką a zamówieniem w bazie danych")
    public ResponseEntity<OrdersBooks> createOrdersBooks(
            @Parameter(description = "Dane nowego powiązania książki z zamówieniem")
            @RequestBody OrdersBooks ordersBooks) {
        return new ResponseEntity<>(ordersBooksService.createOrdersBooks(ordersBooks), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj powiązanie książki z zamówieniem", description = "Aktualizuje szczegóły istniejącego powiązania książki z zamówieniem")
    public ResponseEntity<OrdersBooks> updateOrdersBooks(
            @Parameter(description = "ID powiązania do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Zaktualizowane szczegóły powiązania")
            @RequestBody OrdersBooks ordersBooksDetails) {
        return new ResponseEntity<>(ordersBooksService.updateOrdersBooks(id, ordersBooksDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń powiązanie książki z zamówieniem", description = "Trwale usuwa powiązanie książki z zamówieniem na podstawie ID")
    public ResponseEntity<Void> deleteOrdersBooks(
            @Parameter(description = "ID powiązania do usunięcia", example = "1")
            @PathVariable Long id) {
        ordersBooksService.deleteOrdersBooks(id);
        return ResponseEntity.noContent().build();
    }
}