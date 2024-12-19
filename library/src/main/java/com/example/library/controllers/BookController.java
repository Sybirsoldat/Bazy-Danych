package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books API", description = "API do zarządzania książkami w systemie bibliotecznym")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie książki", description = "Zwraca listę wszystkich książek dostępnych w bazie danych")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz książkę po ID", description = "Zwraca szczegóły książki na podstawie jej unikalnego ID")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID książki, która ma zostać pobrana", example = "1")
            @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Wyszukaj książki", description = "Pozwala wyszukać książki według tytułu, gatunku, roku wydania lub zakresu cen")
    public ResponseEntity<List<Book>> searchBooks(
            @Parameter(description = "Tytuł książki") @RequestParam(required = false) String title,
            @Parameter(description = "ID gatunku książki") @RequestParam(required = false) Long genreId,
            @Parameter(description = "Rok wydania książki") @RequestParam(required = false) Integer publishYear,
            @Parameter(description = "Minimalna cena książki") @RequestParam(required = false) Integer minPrice,
            @Parameter(description = "Maksymalna cena książki") @RequestParam(required = false) Integer maxPrice) {

        if (title != null) return new ResponseEntity<>(bookService.getBooksByTitle(title), HttpStatus.OK);
        if (genreId != null) return new ResponseEntity<>(bookService.getBooksByGenre(genreId), HttpStatus.OK);
        if (publishYear != null) return new ResponseEntity<>(bookService.getBooksByPublishYear(publishYear), HttpStatus.OK);
        if (minPrice != null && maxPrice != null)
            return new ResponseEntity<>(bookService.getBooksByPriceRange(minPrice, maxPrice), HttpStatus.OK);

        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    @Operation(summary = "Dodaj nową książkę", description = "Pozwala dodać nową książkę do bazy danych")
    public ResponseEntity<Book> createBook(
            @Parameter(description = "Dane nowej książki")
            @RequestBody Book book) {
        Book newBook = bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/availability")
    @Operation(summary = "Zaktualizuj dostępność książki", description = "Aktualizuje liczbę dostępnych egzemplarzy książki")
    public ResponseEntity<Book> updateAvailability(
            @Parameter(description = "ID książki do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nowa liczba dostępnych egzemplarzy")
            @RequestParam int totalAmount) {
        Book updatedBook = bookService.updateBookAvailability(id, totalAmount);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/{id}/price")
    @Operation(summary = "Zaktualizuj cenę książki", description = "Aktualizuje cenę książki na podstawie jej ID")
    public ResponseEntity<Book> updateBookPrice(
            @Parameter(description = "ID książki do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nowa cena książki")
            @RequestParam int price) {
        Book updatedBook = bookService.updateBookPrice(id, price);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/soft")
    @Operation(summary = "Ukryj książkę", description = "Usuwa książkę logicznie (ukrywa ją, ale nie usuwa fizycznie)")
    public ResponseEntity<Book> softDeleteBook(
            @Parameter(description = "ID książki do ukrycia", example = "1")
            @PathVariable Long id) {
        Book deletedBook = bookService.softDeleteBook(id);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń książkę", description = "Trwale usuwa książkę z bazy danych")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID książki do usunięcia", example = "1")
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
