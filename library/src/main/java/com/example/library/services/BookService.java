package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByGenre(Long genreId) {
        return bookRepository.findByGenre_Id(genreId);
    }

    public List<Book> getBooksByPublishYear(int year) {
        return bookRepository.findByPublishYear(year);
    }

    public List<Book> getBooksByPriceRange(int minPrice, int maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public Book updateBookPrice(Long id, int newPrice) {
        Book book = getBookById(id);
        book.setPrice(newPrice);
        return bookRepository.save(book);
    }

    public Book updateBookAvailability(Long id, int totalAmount) {
        Book book = getBookById(id);
        if (totalAmount < 0) {
            throw new RuntimeException("Total amount cannot be negative");
        }
        book.setTotalAmount(totalAmount);
        return bookRepository.save(book);
    }

    public Book softDeleteBook(Long id) {
        Book book = getBookById(id);
        book.setDeleted(true);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
