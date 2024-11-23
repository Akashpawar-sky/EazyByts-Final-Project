package com.ak.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
