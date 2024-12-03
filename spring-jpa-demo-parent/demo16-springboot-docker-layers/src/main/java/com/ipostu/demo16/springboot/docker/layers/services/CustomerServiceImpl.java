package com.ipostu.demo16.springboot.docker.layers.services;

import com.ipostu.demo16.springboot.docker.layers.web.model.CustomerDto;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
            .id(UUID.randomUUID())
            .name("Joe Buck")
            .build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
            .id(UUID.randomUUID())
            .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        // todo impl
        log.debug("Updating....");
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting.... ");
    }
}
