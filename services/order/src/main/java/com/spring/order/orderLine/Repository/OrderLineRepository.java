package com.spring.order.orderLine.Repository;

import com.spring.order.orderLine.Model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);

}
