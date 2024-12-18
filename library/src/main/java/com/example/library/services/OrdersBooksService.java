package com.example.library.services;

import com.example.library.models.OrdersBooks;
import com.example.library.repositories.OrdersBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersBooksService {
    @Autowired
    private OrdersBooksRepository ordersBooksRepository;

    public List<OrdersBooks> getAllOrdersBooks() {
        return ordersBooksRepository.findAll();
    }

    public OrdersBooks getOrdersBooksById(Long id) {
        return ordersBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdersBooks entry not found"));
    }

    public List<OrdersBooks> getOrdersBooksByOrderId(Long orderId) {
        return ordersBooksRepository.findByOrder_Id(orderId);
    }

    public List<OrdersBooks> getOrdersBooksByBookId(Long bookId) {
        return ordersBooksRepository.findByBook_Id(bookId);
    }

    public List<OrdersBooks> getIssuedOrdersBooks(Boolean issued) {
        return ordersBooksRepository.findByIssued(issued);
    }

    public OrdersBooks createOrdersBooks(OrdersBooks ordersBooks) {
        return ordersBooksRepository.save(ordersBooks);
    }

    public OrdersBooks updateOrdersBooks(Long id, OrdersBooks ordersBooksDetails) {
        OrdersBooks existingOrdersBooks = getOrdersBooksById(id);

        existingOrdersBooks.setBook(ordersBooksDetails.getBook());
        existingOrdersBooks.setOrder(ordersBooksDetails.getOrder());
        existingOrdersBooks.setQuantity(ordersBooksDetails.getQuantity());
        existingOrdersBooks.setTotalPrice(ordersBooksDetails.getTotalPrice());
        existingOrdersBooks.setIssued(ordersBooksDetails.getIssued());

        return ordersBooksRepository.save(existingOrdersBooks);
    }

    public void deleteOrdersBooks(Long id) {
        OrdersBooks ordersBooks = getOrdersBooksById(id);
        ordersBooksRepository.delete(ordersBooks);
    }
}
