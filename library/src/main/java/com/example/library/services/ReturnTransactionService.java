package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.Book;
import com.example.library.models.Orders;
import com.example.library.models.OrdersBooks;
import com.example.library.repositories.ActivityLogRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.OrdersBooksRepository;
import com.example.library.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReturnTransactionService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    /**
     * Obsługa zwrotu wszystkich książek z zamówienia.
     */
    @Transactional
    public Orders returnBooks(Long orderId, Long userId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Iteruj przez wszystkie książki w zamówieniu
        ordersBooksRepository.findByOrder_Id(orderId).forEach(orderBook -> {
            if (!orderBook.getIssued()) {
                throw new RuntimeException("Book not issued in this order: " + orderBook.getBook().getId());
            }

            // Zwiększ ilość dostępnych książek w magazynie
            Book book = orderBook.getBook();
            book.setTotalAmount(book.getTotalAmount() + orderBook.getQuantity());
            bookRepository.save(book);

            // Ustaw `issued` na false
            orderBook.setIssued(false);
            ordersBooksRepository.save(orderBook);
        });

        // Dodaj wpis do dziennika aktywności
        ActivityLog log = new ActivityLog();
        log.setUser(order.getUser()); // Ustaw użytkownika powiązanego z zamówieniem
        log.setAction("Returned all books for Order ID: " + orderId);
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);

        return order;
    }
}
