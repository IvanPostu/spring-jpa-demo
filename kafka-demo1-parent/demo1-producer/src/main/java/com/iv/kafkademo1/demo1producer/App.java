package com.iv.kafkademo1.demo1producer;

import com.iv.kafkademo1.demo1producer.entity.Employee;
import com.iv.kafkademo1.demo1producer.entity.PaymentRequest;
import com.iv.kafkademo1.demo1producer.entity.PurchaseRequest;
import com.iv.kafkademo1.demo1producer.producer.*;
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

    @Autowired
    private CounterProducer counterProducer;

    @Autowired
    private PurchaseRequestProducer purchaseRequestProducer;

    @Autowired
    private PaymentRequestProducer paymentRequestProducer;

    @Override
    public void run(String... args) throws Exception {
//        sendMessagesInLoop();
//        generateAndPublishEmployees(5);
//        counterProducer.sendMessage(100);
//        sendPurchaseRequests();

        sendPaymentRequests();
    }

    private void sendPaymentRequests() throws Exception {
        PaymentRequest request1 = new PaymentRequest(UUID.randomUUID().toString(), 100, "USD", "test1", "debit");
        PaymentRequest request2 = new PaymentRequest(UUID.randomUUID().toString(), 200, "EUR", "test2", "debit");
        PaymentRequest request3 = new PaymentRequest(UUID.randomUUID().toString(), 300, "GBP", "test3", "debit");

        paymentRequestProducer.send(request1);
        paymentRequestProducer.send(request2);
        paymentRequestProducer.send(request3);

        paymentRequestProducer.send(request1);
    }

    private void sendPurchaseRequests() throws Exception {
        PurchaseRequest purchaseRequest1 = new PurchaseRequest(1, "REQ-001", 100, "USD");
        PurchaseRequest purchaseRequest2 = new PurchaseRequest(2, "REQ-002", 200, "EUR");
        PurchaseRequest purchaseRequest3 = new PurchaseRequest(3, "REQ-003", 300, "GBP");

        purchaseRequestProducer.send(purchaseRequest1);
        purchaseRequestProducer.send(purchaseRequest2);
        purchaseRequestProducer.send(purchaseRequest3);

        purchaseRequestProducer.send(purchaseRequest1);
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
