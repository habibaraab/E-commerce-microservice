package com.spring.order.orderLine.DTO.Mapper;

import com.spring.order.orderLine.DTO.OrderLineRequest;
import com.spring.order.orderLine.DTO.OrderLineResponse;
import com.spring.order.orderLine.Model.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "quantity", source = "quantity")
    OrderLine toOrderLine(OrderLineRequest request);


    OrderLineResponse toOrderLineResponse(OrderLine orderLine);
}