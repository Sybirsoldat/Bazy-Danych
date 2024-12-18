package com.example.library.repositories;

import com.example.library.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_Id(Long userId);
    List<Orders> findByIsActive(Boolean isActive);
}