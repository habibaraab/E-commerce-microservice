package com.spring.order.order.Repository;

import com.spring.order.order.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
