package com.spring.customer.Exception;

import java.util.Map;

public record ErrorResponse(

        Map<String, String> errors

) {
}
