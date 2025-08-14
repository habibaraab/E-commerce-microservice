package com.spring.order.orderLine.Service;


import com.spring.order.order.Repository.OrderRepository;
import com.spring.order.orderLine.DTO.Mapper.OrderLineMapper;
import com.spring.order.orderLine.DTO.OrderLineRequest;
import com.spring.order.orderLine.DTO.OrderLineResponse;
import com.spring.order.orderLine.Model.OrderLine;
import com.spring.order.orderLine.Repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest  orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        orderLineRepository.save(orderLine);
        return orderLine.getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
