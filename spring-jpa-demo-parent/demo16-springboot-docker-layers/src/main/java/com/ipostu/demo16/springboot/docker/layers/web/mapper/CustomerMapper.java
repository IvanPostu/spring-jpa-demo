package com.ipostu.demo16.springboot.docker.layers.web.mapper;

import com.ipostu.demo16.springboot.docker.layers.domain.Customer;
import com.ipostu.demo16.springboot.docker.layers.web.model.CustomerDto;

import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto dto);

    CustomerDto customerToCustomerDto(Customer customer);
}
