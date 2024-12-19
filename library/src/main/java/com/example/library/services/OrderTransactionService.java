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

import java.util.List;

@Service
public class OrderTransactionService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Tworzy zamówienie i rezerwuje książki.
     */
    @Transactional
    public Orders createOrderWithBooks(Orders order, List<OrdersBooks> books) {
        Orders savedOrder = ordersRepository.save(order);

        for (OrdersBooks orderBook : books) {
            Book book = bookRepository.findById(orderBook.getBook().getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getTotalAmount() < orderBook.getQuantity()) {
                throw new RuntimeException("Not enough copies available.");
            }

            // Rezerwacja książek - zmniejszenie ilości dostępnych
            book.setTotalAmount(book.getTotalAmount() - orderBook.getQuantity());
            bookRepository.save(book);

            // Zapisz zamówienie książki
            orderBook.setOrder(savedOrder);
            orderBook.setIssued(false);  // Zarezerwowane, ale jeszcze niewydane
            orderBook.setTotalPrice((double) (book.getPrice() * orderBook.getQuantity()));
            ordersBooksRepository.save(orderBook);
        }
        return savedOrder;
    }
}
