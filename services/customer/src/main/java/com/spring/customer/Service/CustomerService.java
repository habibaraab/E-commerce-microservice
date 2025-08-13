package com.spring.customer.Service;

import com.spring.customer.DTO.CustomerRequest;
import com.spring.customer.DTO.CustomerResponse;
import com.spring.customer.DTO.Mapper.CustomerMapper;
import com.spring.customer.Exception.CustomerNotFoundException;
import com.spring.customer.Model.Customer;
import com.spring.customer.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse  getCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException(
                "There is no customer with id " + id));
        return customerMapper.fromCustomer(customer);

    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.
                stream().
                map(customerMapper::fromCustomer).
                collect(Collectors.toList());
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(request));
        return  customerMapper.fromCustomer(customer);
    }


    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));
        customerMapper.updateCustomerFromRequest(request, customer);
        customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        this.customerRepository.deleteById(id);
    }


}


