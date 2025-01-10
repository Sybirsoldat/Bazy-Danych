package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.models.Orders;
import com.example.library.models.User;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeleteUserTransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ActivityLogService activityLogService;

    /**
     * Completely deletes a user from the system.
     */
    @Transactional
    public void deleteUser(Long userId) {
        // Retrieve user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete activity logs
        activityLogService.deleteActivityLogsByUserId(userId);

        // Check if user has active orders
        List<Orders> activeOrders = ordersService.getOrdersByUserId(userId);
        if (activeOrders.stream().anyMatch(Orders::getIsActive)) {
            throw new RuntimeException("User cannot be deleted while having active orders.");
        }




        // Delete the user
        userRepository.delete(user);

        // Log activity
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setAction("Deleted user with ID: " + userId);
        log.setTimestamp(LocalDateTime.now());
        activityLogService.createActivityLog(log);
    }
}
