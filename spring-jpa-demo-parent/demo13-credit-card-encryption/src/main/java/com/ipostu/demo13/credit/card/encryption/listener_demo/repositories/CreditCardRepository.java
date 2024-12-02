package com.ipostu.demo13.credit.card.encryption.listener_demo.repositories;

import com.ipostu.demo13.credit.card.encryption.listener_demo.domain.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("listenerCreditCardRepository")
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
