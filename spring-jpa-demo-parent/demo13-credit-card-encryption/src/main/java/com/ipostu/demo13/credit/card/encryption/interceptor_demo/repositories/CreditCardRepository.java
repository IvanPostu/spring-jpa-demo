package com.ipostu.demo13.credit.card.encryption.interceptor_demo.repositories;


import com.ipostu.demo13.credit.card.encryption.interceptor_demo.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("interceptorCreditCardRepository")
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
