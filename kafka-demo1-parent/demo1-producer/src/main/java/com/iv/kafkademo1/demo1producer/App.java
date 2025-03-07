package com.iv.kafkademo1.demo1producer;

import com.iv.kafkademo1.demo1producer.producer.HelloKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private HelloKafkaProducer helloKafkaProducer;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String message = "Test " + ThreadLocalRandom.current().nextInt(100);
            helloKafkaProducer.sendHello(message);
            LOG.info("Produced message: {}", message);
            Thread.sleep(1200);
        }
    }
}
