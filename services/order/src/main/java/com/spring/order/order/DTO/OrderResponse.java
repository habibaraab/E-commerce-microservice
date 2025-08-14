package com.spring.order.order.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.order.order.Enum.PaymentMethod;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
