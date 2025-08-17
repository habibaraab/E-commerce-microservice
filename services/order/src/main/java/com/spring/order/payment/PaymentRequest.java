package com.spring.order.payment;

import com.spring.order.customer.CustomerResponse;
import com.spring.order.order.Enum.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String reference,
        CustomerResponse customer
) {
}