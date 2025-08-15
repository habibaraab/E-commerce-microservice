package com.spring.payment.Exception;

import java.util.Map;
public record ErrorResponse(
        Map<String, String> errors
) {

}