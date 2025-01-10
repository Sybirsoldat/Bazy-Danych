package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.ActivityLog;
import com.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddBookTransactionService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ActivityLogService activityLogService;

    /**
     * Adds a new book to the system.
     */
    @Transactional
    public Book addNewBook(Book bookDetails) {
        // Check if a book with the same title and publication year already exists
        boolean exists = bookRepository.findAll().stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(bookDetails.getTitle())
                        && book.getPublishYear() == bookDetails.getPublishYear());

        if (exists) {
            throw new RuntimeException("A book with the same title and publication year already exists.");
        }

        // Save the new book to the database
        Book savedBook = bookRepository.save(bookDetails);

        // Add an entry to the activity log
        ActivityLog log = new ActivityLog();
        log.setAction("Added a new book: " + bookDetails.getTitle() + " (" + bookDetails.getPublishYear() + ")");
        activityLogService.createActivityLog(log);

        // Return the saved book
        return savedBook;
    }
}
