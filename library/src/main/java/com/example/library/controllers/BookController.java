package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) Long genreId,
                                                  @RequestParam(required = false) Integer publishYear,
                                                  @RequestParam(required = false) Integer minPrice,
                                                  @RequestParam(required = false) Integer maxPrice) {
        if (title != null) return new ResponseEntity<>(bookService.getBooksByTitle(title), HttpStatus.OK);
        if (genreId != null) return new ResponseEntity<>(bookService.getBooksByGenre(genreId), HttpStatus.OK);
        if (publishYear != null) return new ResponseEntity<>(bookService.getBooksByPublishYear(publishYear), HttpStatus.OK);
        if (minPrice != null && maxPrice != null) return new ResponseEntity<>(bookService.getBooksByPriceRange(minPrice, maxPrice), HttpStatus.OK);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/availability")
    public ResponseEntity<Book> updateAvailability(@PathVariable Long id, @RequestParam int totalAmount) {
        Book updatedBook = bookService.updateBookAvailability(id, totalAmount);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Book> updateBookPrice(@PathVariable Long id, @RequestParam int price) {
        Book updatedBook = bookService.updateBookPrice(id, price);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Book> softDeleteBook(@PathVariable Long id) {
        Book deletedBook = bookService.softDeleteBook(id);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
