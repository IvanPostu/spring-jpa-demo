package com.ipostu.demo13.credit.card.encryption.converter_demo.domain;

import com.ipostu.demo13.credit.card.encryption.jpa_callback_demo.config.SpringContextHelper;
import com.ipostu.demo13.credit.card.encryption.services.EncryptionService;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return getEncryptionService().encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return getEncryptionService().decrypt(dbData);
    }

    private EncryptionService getEncryptionService() {
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }
}
