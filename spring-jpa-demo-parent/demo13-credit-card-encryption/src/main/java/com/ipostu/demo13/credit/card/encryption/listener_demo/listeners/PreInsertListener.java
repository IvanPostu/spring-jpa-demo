package com.ipostu.demo13.credit.card.encryption.listener_demo.listeners;

import com.ipostu.demo13.credit.card.encryption.services.EncryptionService;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreInsertListener extends AbstractEncryptionListener implements PreInsertEventListener {

    public PreInsertListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        System.out.println("In Pre Insert");

        this.encrypt(event.getState(), event.getPersister().getPropertyNames(), event.getEntity());

        return false;
    }
}
