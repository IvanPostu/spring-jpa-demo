package com.ipostu.demo10.hibernate_mappings.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ipostu.demo10.hibernate_mappings.domain.Address;
import com.ipostu.demo10.hibernate_mappings.domain.Customer;
import com.ipostu.demo10.hibernate_mappings.domain.OrderApproval;
import com.ipostu.demo10.hibernate_mappings.domain.OrderHeader;
import com.ipostu.demo10.hibernate_mappings.domain.OrderLine;
import com.ipostu.demo10.hibernate_mappings.domain.Product;
import com.ipostu.demo10.hibernate_mappings.domain.ProductStatus;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("test product");
        product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    void testEntityValidationProgrammatically() {
        Customer customer = new Customer();
        customer.setCustomerName("New CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew");

        var address = new Address();
        address.setCity("Los Angels 123456789123456789123456789123456789");
        customer.setAddress(address);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).hasSize(2);
        assertThat(violations)
            .anySatisfy(constraintViolation -> {
                assertThat(constraintViolation.getInvalidValue())
                    .isEqualTo("New CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew");
                assertThat(constraintViolation.getPropertyPath())
                    .hasToString("customerName");
                assertThat(constraintViolation.getMessage())
                    .isEqualTo("length must be between 0 and 50");
            })
            .anySatisfy(constraintViolation -> {
                assertThat(constraintViolation.getInvalidValue())
                    .isEqualTo("Los Angels 123456789123456789123456789123456789");
                assertThat(constraintViolation.getPropertyPath())
                    .hasToString("address.city");
                assertThat(constraintViolation.getMessage())
                    .isEqualTo("length must be between 0 and 30");
            });
    }

    @Test
    void testEntityValidation() {
        Customer customer = new Customer();
        customer.setCustomerName("New CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew");

        var address = new Address();
        address.setCity("Los Angels 123456789123456789123456789123456789");
        customer.setAddress(address);

        ConstraintViolationException e =
            assertThrows(ConstraintViolationException.class, () -> customerRepository.save(customer));
        assertThat(e.getConstraintViolations()).hasSize(2);
        assertThat(e.getConstraintViolations())
            .anySatisfy(constraintViolation -> {
                assertThat(constraintViolation.getInvalidValue())
                    .isEqualTo("New CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew CustomerNew");
                assertThat(constraintViolation.getPropertyPath())
                    .hasToString("customerName");
                assertThat(constraintViolation.getMessage())
                    .isEqualTo("length must be between 0 and 50");
            })
            .anySatisfy(constraintViolation -> {
                assertThat(constraintViolation.getInvalidValue())
                    .isEqualTo("Los Angels 123456789123456789123456789123456789");
                assertThat(constraintViolation.getPropertyPath())
                    .hasToString("address.city");
                assertThat(constraintViolation.getMessage())
                    .isEqualTo("length must be between 0 and 30");
            });
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        Customer savedCustomer = customerRepository.save(customer);

        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);

        OrderApproval approval = new OrderApproval();
        approval.setApprovedBy("me");

        orderHeader.setOrderApproval(approval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        orderHeaderRepository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        Customer savedCustomer = customerRepository.save(customer);

        orderHeader.setCustomer(savedCustomer);
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

    @Test
    void testDeleteCascade() {

        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("new Customer");
        orderHeader.setCustomer(customerRepository.save(customer));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderHeader.setOrderApproval(orderApproval);

        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("order saved and flushed");

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        assertThat(orderHeaderRepository.findById(savedOrder.getId()))
            .isEmpty();
    }

}
