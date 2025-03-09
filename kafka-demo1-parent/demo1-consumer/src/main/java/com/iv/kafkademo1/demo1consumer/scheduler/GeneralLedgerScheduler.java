package com.iv.kafkademo1.demo1consumer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GeneralLedgerScheduler {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerScheduler.class);

    /**
     * ┌──────────── Second (0-59)
     * │ ┌────────── Minute (0-59)
     * │ │ ┌──────── Hour (0-23) → 15 (3 PM)
     * │ │ │ ┌────── Day of Month (1-31)
     * │ │ │ │ ┌──── Month (1-12)
     * │ │ │ │ │ ┌── Day of Week (1-7 or SUN-SAT)
     * │ │ │ │ │ │ ┌── (Optional) Year
     *     7  15 *  *  ?
     */
    @Scheduled(cron = "0 30 15 * * ?")
    public void stop() {
        LOG.info("Stopping consumer");
        registry.getListenerContainer("general-ledger.one").pause();
    }

    @Scheduled(cron = "1 32 15 * * ?")
    public void start() {
        LOG.info("Starting consumer");
        registry.getListenerContainer("general-ledger.one").resume();
    }

}
