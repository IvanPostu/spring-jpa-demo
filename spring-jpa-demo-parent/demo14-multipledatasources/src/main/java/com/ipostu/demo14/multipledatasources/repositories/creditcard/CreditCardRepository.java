package com.ipostu.demo14.multipledatasources.repositories.creditcard;

import com.ipostu.demo14.multipledatasources.domain.card.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
