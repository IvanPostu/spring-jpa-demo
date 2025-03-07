package com.iv.kafkademo1.demo1producer;

import com.iv.kafkademo1.demo1producer.producer.KafkaKeyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class App implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private KafkaKeyProducer kafkaKeyProducer;

    @Override
    public void run(String... args) throws Exception {
        int i = 0;
        while (true) {
            i++;
            String message = "Test " + ThreadLocalRandom.current().nextInt(100);
            String key = "key-" + i;
            kafkaKeyProducer.send(key, message);
            LOG.info("Produced key: {}, message: {}", key, message);
            Thread.sleep(1200);
        }
    }
}
