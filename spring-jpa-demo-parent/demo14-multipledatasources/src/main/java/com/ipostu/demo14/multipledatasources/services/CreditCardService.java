package com.ipostu.demo14.multipledatasources.services;

import com.ipostu.demo14.multipledatasources.domain.card.CreditCard;

public interface CreditCardService {

    CreditCard getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard cc);
}
