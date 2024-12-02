package com.ipostu.demo13.credit.card.encryption.listener_demo.listeners;

import com.ipostu.demo13.credit.card.encryption.services.EncryptionService;

import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreUpdateListener extends AbstractEncryptionListener implements PreUpdateEventListener {

    public PreUpdateListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        System.out.println("In Pre Update");

        this.encrypt(event.getState(), event.getPersister().getPropertyNames(), event.getEntity());

        return false;
    }
}
