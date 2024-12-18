package com.example.library.repositories;

import com.example.library.models.OrdersBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersBooksRepository extends JpaRepository<OrdersBooks, Long> {
    List<OrdersBooks> findByOrder_Id(Long orderId);
    List<OrdersBooks> findByBook_Id(Long bookId);
    List<OrdersBooks> findByIssued(Boolean issued);
}