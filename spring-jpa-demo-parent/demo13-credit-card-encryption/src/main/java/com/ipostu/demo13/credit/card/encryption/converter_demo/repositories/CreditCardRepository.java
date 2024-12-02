package com.ipostu.demo13.credit.card.encryption.converter_demo.repositories;

import com.ipostu.demo13.credit.card.encryption.converter_demo.domain.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("converterCreditCardRepository")
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
