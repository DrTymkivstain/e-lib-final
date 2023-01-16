package com.example.elibfinal.repository;

import com.example.elibfinal.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByActiveIsTrue();

    List<Order> findAllByActiveIsFalse();

    List<Order> findAllByActiveIsTrueAndUser_Username(String name);

    List<Order> findAllByActiveIsFalseAndUser_Username(String name);
}
