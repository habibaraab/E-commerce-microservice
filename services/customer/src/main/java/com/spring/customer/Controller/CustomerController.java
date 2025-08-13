package com.spring.customer.Controller;

import com.spring.customer.DTO.CustomerRequest;
import com.spring.customer.DTO.CustomerResponse;
import com.spring.customer.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse>findCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse=customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerResponseList=customerService.getAllCustomers();
        return ResponseEntity.ok(customerResponseList);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest,@PathVariable String id) {
        this.customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable String id) {
        this.customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }



}
