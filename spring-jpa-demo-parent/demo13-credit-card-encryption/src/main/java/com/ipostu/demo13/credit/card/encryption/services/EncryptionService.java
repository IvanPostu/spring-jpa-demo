package com.ipostu.demo13.credit.card.encryption.services;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);
}
