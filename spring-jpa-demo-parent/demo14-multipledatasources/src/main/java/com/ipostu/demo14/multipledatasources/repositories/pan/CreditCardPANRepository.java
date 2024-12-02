package com.ipostu.demo14.multipledatasources.repositories.pan;

import com.ipostu.demo14.multipledatasources.domain.pan.CreditCardPAN;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {
    Optional<CreditCardPAN> findByCreditCardId(Long id);
}
