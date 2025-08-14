package com.spring.order.order.DTO.Mapper;


import com.spring.order.order.DTO.OrderRequest;
import com.spring.order.order.DTO.OrderResponse;
import com.spring.order.order.Model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(source = "amount", target = "totalAmount")
    Order toOrder(OrderRequest request);

    @Mapping(source = "totalAmount", target = "amount")
    OrderResponse fromOrder(Order order);
}