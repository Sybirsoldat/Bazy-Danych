package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.Orders;
import com.example.library.models.OrdersBooks;
import com.example.library.user.User;
import com.example.library.repositories.ActivityLogRepository;
import com.example.library.repositories.OrdersBooksRepository;
import com.example.library.repositories.OrdersRepository;
import com.example.library.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueBookTransactionService {

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserService userService;


    /**
     * Wydanie książek z zamówienia.
     */
    @Transactional
    public Orders issueBooks(Long orderId, Long bookId) {
        // Fetch the order
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Get the user associated with the order
        User user = order.getUser(); // Assuming the Orders entity has a User reference

        // Check for book reservation
        OrdersBooks orderBook = ordersBooksRepository.findByOrder_Id(orderId).stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not reserved"));

        if (orderBook.getIssued()) {
            throw new RuntimeException("Book already issued.");
        }

        // Update issued quantity
        orderBook.setIssued(true);  // Mark as issued if all books are issued

        ordersBooksRepository.save(orderBook);

        // Log activity
        ActivityLog log = new ActivityLog();
        log.setUser(user); // Use the User fetched from the Orders entity
        log.setAction("Issued " + orderBook.getQuantity() + " copies of Book ID: " + bookId + " for Order ID: " + orderId);
        log.setTimestamp(java.time.LocalDateTime.now());
        activityLogRepository.save(log);

        return order;
    }

}

