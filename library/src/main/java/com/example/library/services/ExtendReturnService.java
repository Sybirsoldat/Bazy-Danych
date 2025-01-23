// --- SERWIS PRZEDŁUŻANIA TERMINU ZWROTU KSIĄŻKI ---

package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.Orders;
import com.example.library.user.User;
import com.example.library.models.OrdersBooks;
import com.example.library.repositories.ActivityLogRepository;
import com.example.library.repositories.OrdersBooksRepository;
import com.example.library.repositories.OrdersRepository;
import com.example.library.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExtendReturnService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserService userService;


    /**
     * Przedłużenie terminu zwrotu książki.
     */
    @Transactional
    public Orders extendReturnDate(Long orderId, Long bookId, int additionalDays, Long userId) {
        // Znajdź zamówienie
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Sprawdź, czy zamówienie jest aktywne
        if (!Boolean.TRUE.equals(order.getIsActive())) {
            throw new RuntimeException("Order is not active");
        }

        // Znajdź powiązanie książki z zamówieniem
        Optional<OrdersBooks> orderBookOpt = ordersBooksRepository.findByOrder_Id(orderId).stream()
                .filter(ob -> ob.getBook().getId().equals(bookId) && Boolean.TRUE.equals(ob.getIssued()))
                .findFirst();

        if (orderBookOpt.isEmpty()) {
            throw new RuntimeException("Book is not issued as part of this order");
        }

        // Przedłuż termin zwrotu
        LocalDate currentDateTo = order.getDateTo() != null ?
                order.getDateTo().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() :
                LocalDate.now();
        LocalDate newDateTo = currentDateTo.plusDays(additionalDays);
        order.setDateTo(java.sql.Date.valueOf(newDateTo));

        ordersRepository.save(order);

        User user = userService.getUserById(userId);

        ActivityLog log = new ActivityLog();
        log.setUser(user); // Set the User entity
        log.setAction("Extended return date for Order ID: " + orderId + " by " + additionalDays + " days");
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);

        return order;
    }
}
