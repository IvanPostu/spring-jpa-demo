package com.ipostu.demo13.credit.card.encryption.interceptor_demo.services;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);
}
