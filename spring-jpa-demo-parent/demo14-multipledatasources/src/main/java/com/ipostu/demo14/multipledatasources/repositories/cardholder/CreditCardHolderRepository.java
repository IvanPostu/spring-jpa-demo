package com.ipostu.demo14.multipledatasources.repositories.cardholder;

import com.ipostu.demo14.multipledatasources.domain.cardholder.CreditCardHolder;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
    Optional<CreditCardHolder> findByCreditCardId(Long id);
}
