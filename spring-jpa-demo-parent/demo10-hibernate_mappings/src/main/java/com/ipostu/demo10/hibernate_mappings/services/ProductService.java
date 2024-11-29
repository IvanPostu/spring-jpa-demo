package com.ipostu.demo10.hibernate_mappings.services;

import com.ipostu.demo10.hibernate_mappings.domain.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);
}
