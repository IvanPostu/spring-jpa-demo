package com.ipostu.demo13.credit.card.encryption.jpa_callback_demo.repositories;


import com.ipostu.demo13.credit.card.encryption.jpa_callback_demo.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("callbackCreditCardRepository")
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
