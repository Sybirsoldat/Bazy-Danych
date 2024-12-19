package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Orders;
import com.example.library.models.OrdersBooks;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.OrdersBooksRepository;
import com.example.library.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueBookTransactionService {

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    /**
     * Wydanie książek z zamówienia.
     */
    @Transactional
    public Orders issueBooks(Long orderId, Long bookId, int issueQuantity) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Sprawdź, czy istnieje odpowiedni wpis w OrdersBooks
        OrdersBooks orderBook = ordersBooksRepository.findByOrder_Id(orderId).stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not reserved"));

        if (orderBook.getIssued()) {
            throw new RuntimeException("Book already issued.");
        }

        if (orderBook.getQuantity() < issueQuantity) {
            throw new RuntimeException("Not enough reserved books.");
        }

        // Aktualizacja ilości wydanych książek
        orderBook.setQuantity(orderBook.getQuantity() - issueQuantity);
        orderBook.setIssued(orderBook.getQuantity() == 0);  // Jeśli wszystkie wydane, ustaw `issued = true`

        ordersBooksRepository.save(orderBook);
        return order;
    }
}
