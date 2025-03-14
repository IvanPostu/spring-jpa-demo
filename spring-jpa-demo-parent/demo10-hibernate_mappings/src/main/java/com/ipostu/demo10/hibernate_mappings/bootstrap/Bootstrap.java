package com.ipostu.demo10.hibernate_mappings.bootstrap;

import com.ipostu.demo10.hibernate_mappings.domain.Customer;
import com.ipostu.demo10.hibernate_mappings.repositories.CustomerRepository;
import com.ipostu.demo10.hibernate_mappings.repositories.OrderHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    BootstrapOrderService bootstrapOrderService;

    @Autowired
    CustomerRepository customerRepository;
    // @Transactional
    // public void readOrderData(){
    // OrderHeader orderHeader = orderHeaderRepository.findById(1L).get();
    //
    // orderHeader.getOrderLines().forEach(ol -> {
    // System.out.println(ol.getProduct().getDescription());
    //
    // ol.getProduct().getCategories().forEach(cat -> {
    // System.out.println(cat.getDescription());
    // });
    // });
    // }

    @Override
    public void run(String... args) throws Exception {
        bootstrapOrderService.readOrderData();

        Customer customer = new Customer();
        customer.setCustomerName("Testing Version");
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("Version is: " + savedCustomer.getVersion());

        savedCustomer.setCustomerName("Testing Version 2");
        Customer savedCustomer2 = customerRepository.save(savedCustomer);
        System.out.println("Version is: " + savedCustomer2.getVersion());

        savedCustomer2.setCustomerName("Testing Version 3");
        Customer savedCustomer3 = customerRepository.save(savedCustomer2);
        System.out.println("Version is: " + savedCustomer3.getVersion());

        customerRepository.delete(savedCustomer3);
    }
}
