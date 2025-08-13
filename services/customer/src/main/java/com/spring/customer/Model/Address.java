package com.spring.customer.Model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
