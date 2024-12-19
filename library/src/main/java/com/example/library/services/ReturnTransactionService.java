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
public class ReturnTransactionService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Obsługa zwrotu części książek
     */
    @Transactional
    public Orders returnBooks(Long orderId, Long bookId, int returnQuantity) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrdersBooks orderBook = ordersBooksRepository.findByOrder_Id(orderId).stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found in order"));

        if (!orderBook.getIssued() || returnQuantity <= 0) {
            throw new RuntimeException("Invalid return quantity.");
        }

        if (returnQuantity > orderBook.getQuantity()) {
            throw new RuntimeException("Return quantity exceeds issued quantity.");
        }

        // Aktualizuj ilość książek w magazynie
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTotalAmount(book.getTotalAmount() + returnQuantity);
        bookRepository.save(book);

        // Aktualizacja ilości w OrdersBooks
        int remainingQuantity = orderBook.getQuantity() - returnQuantity;

        if (remainingQuantity > 0) {
            // Zaktualizuj ilość w istniejącym zamówieniu
            orderBook.setQuantity(remainingQuantity);
            ordersBooksRepository.save(orderBook);

            // Dodaj nowy rekord dla zwróconych książek
            OrdersBooks returnedOrderBook = new OrdersBooks();
            returnedOrderBook.setBook(book);
            returnedOrderBook.setOrder(order);
            returnedOrderBook.setQuantity(returnQuantity);
            returnedOrderBook.setTotalPrice((double) (returnQuantity * book.getPrice()));
            returnedOrderBook.setIssued(false);  // Książki są zwrócone
            ordersBooksRepository.save(returnedOrderBook);
        } else {
            // Jeśli zwrócono wszystko, oznacz rekord jako niewydany
            orderBook.setIssued(false);
            orderBook.setQuantity(0);  // Zeruj ilość
            ordersBooksRepository.save(orderBook);
        }

        return order;
    }
}
