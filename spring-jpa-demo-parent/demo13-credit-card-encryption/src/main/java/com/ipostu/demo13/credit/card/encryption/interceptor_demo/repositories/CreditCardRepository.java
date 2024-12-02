package com.ipostu.demo13.credit.card.encryption.interceptor_demo.repositories;

import com.ipostu.demo13.credit.card.encryption.interceptor_demo.domain.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
