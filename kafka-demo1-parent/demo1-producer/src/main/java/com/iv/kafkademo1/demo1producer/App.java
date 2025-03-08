package com.iv.kafkademo1.demo1producer;

import com.iv.kafkademo1.demo1producer.entity.Employee;
import com.iv.kafkademo1.demo1producer.producer.EmployeeJsonProducer;
import com.iv.kafkademo1.demo1producer.producer.KafkaKeyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.UUID;
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

    @Autowired
    private EmployeeJsonProducer employeeJsonProducer;

    @Override
    public void run(String... args) throws Exception {
//        sendMessagesInLoop();
        generateAndPublishEmployees(5);
    }

    private void sendMessagesInLoop() throws InterruptedException {
        while (true) {
            int randomInt = ThreadLocalRandom.current().nextInt(100);
            String message = "Test " + randomInt;
            String key = "key-" + randomInt;
            kafkaKeyProducer.send(key, message);
            LOG.info("Produced key: {}, message: {}", key, message);
            Thread.sleep(100);
        }
    }

    private void generateAndPublishEmployees(int count) throws Exception {
        for (int i = 0; i < count; i++) {
            Employee employee = new Employee(UUID.randomUUID().toString(),
                    "name-" + i, LocalDate.now());
            employeeJsonProducer.sendMessage(employee);
            LOG.info("Produced message: {}", employee);
        }
    }
}
