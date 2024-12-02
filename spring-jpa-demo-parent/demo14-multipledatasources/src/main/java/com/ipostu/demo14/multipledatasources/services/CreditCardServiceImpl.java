package com.ipostu.demo14.multipledatasources.services;

import com.ipostu.demo14.multipledatasources.domain.card.CreditCard;
import com.ipostu.demo14.multipledatasources.domain.cardholder.CreditCardHolder;
import com.ipostu.demo14.multipledatasources.domain.pan.CreditCardPAN;
import com.ipostu.demo14.multipledatasources.repositories.cardholder.CreditCardHolderRepository;
import com.ipostu.demo14.multipledatasources.repositories.creditcard.CreditCardRepository;
import com.ipostu.demo14.multipledatasources.repositories.pan.CreditCardPANRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardPANRepository creditCardPANRepository;

    @Transactional
    @Override
    public CreditCard getCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        CreditCardHolder creditCardHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
        CreditCardPAN creditCardPAN = creditCardPANRepository.findByCreditCardId(id).orElseThrow();

        creditCard.setFirstName(creditCardHolder.getFirstName());
        creditCard.setLastName(creditCardHolder.getLastName());
        creditCard.setZipCode(creditCard.getZipCode());
        creditCard.setCreditCardNumber(creditCardPAN.getCreditCardNumber());

        return creditCard;
    }

    @Transactional
    @Override
    public CreditCard saveCreditCard(CreditCard cc) {
        CreditCard savedCC = creditCardRepository.save(cc);
        savedCC.setFirstName(cc.getFirstName());
        savedCC.setLastName(cc.getLastName());
        savedCC.setZipCode(cc.getCreditCardNumber());
        savedCC.setCreditCardNumber(cc.getCreditCardNumber());

        creditCardHolderRepository.save(CreditCardHolder.builder()
            .creditCardId(savedCC.getId())
            .firstName(savedCC.getFirstName())
            .lastName(savedCC.getLastName())
            .zipCode(savedCC.getZipCode())
            .build());

        creditCardPANRepository.save(CreditCardPAN.builder()
            .creditCardNumber(savedCC.getCreditCardNumber())
            .creditCardId(savedCC.getId())
            .build());

        return savedCC;
    }
}
