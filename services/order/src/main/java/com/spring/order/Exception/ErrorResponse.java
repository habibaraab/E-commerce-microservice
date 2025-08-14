package com.spring.order.Exception;

import java.util.Map;

public record ErrorResponse(
        Map<String,String>errors
) {
}
