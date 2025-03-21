package com.ipostu.demo16.springboot.docker.layers.services;

import com.ipostu.demo16.springboot.docker.layers.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID customerId);

    CustomerDto saveNewCustomer(CustomerDto customerDto);

    void updateCustomer(UUID customerId, CustomerDto customerDto);

    void deleteById(UUID customerId);
}
