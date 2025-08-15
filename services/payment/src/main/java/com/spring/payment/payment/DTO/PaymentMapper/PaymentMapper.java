package com.spring.payment.payment.DTO.PaymentMapper;

import com.spring.payment.payment.DTO.PaymentRequest;
import com.spring.payment.payment.Model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "orderId", source = "orderId")
    Payment toPayment(PaymentRequest request);
}