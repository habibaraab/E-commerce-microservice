package com.spring.customer.DTO;

import com.spring.customer.Model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
