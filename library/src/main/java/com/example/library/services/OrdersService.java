package com.example.library.services;

import com.example.library.models.Orders;
import com.example.library.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Orders> getOrdersByUserId(Long userId) {
        return ordersRepository.findByUser_Id(userId);
    }

    public List<Orders> getActiveOrders() {
        return ordersRepository.findByIsActive(true);
    }

    public Orders createOrder(Orders order) {
        order.setOrderDate(new Date());
        order.setIsActive(true);
        return ordersRepository.save(order);
    }

    public Orders updateOrder(Long id, Orders orderDetails) {
        Orders existingOrder = getOrderById(id);

        existingOrder.setDateFrom(orderDetails.getDateFrom());
        existingOrder.setDateTo(orderDetails.getDateTo());
        existingOrder.setIsActive(orderDetails.getIsActive());

        return ordersRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Orders order = getOrderById(id);
        ordersRepository.delete(order);
    }
}
