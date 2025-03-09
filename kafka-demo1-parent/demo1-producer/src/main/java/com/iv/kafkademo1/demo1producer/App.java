package com.iv.kafkademo1.demo1producer;

import com.iv.kafkademo1.demo1common.entity.*;
import com.iv.kafkademo1.demo1producer.producer.*;
import com.iv.kafkademo1.demo1producer.service.ImageService;
import com.iv.kafkademo1.demo1producer.service.InvoiceService;
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
    private PurchaseRequestProducer2 purchaseRequestProducer2;

    @Autowired
    private PaymentRequestProducer paymentRequestProducer;

    @Autowired
    private FoodOrderProducer foodOrderProducer;

    @Autowired
    private SimpleNumberProducer simpleNumberProducer;

    @Autowired
    private ImageProducer imageProducer;
    @Autowired
    private Image2Producer image2Producer;

    @Autowired
    private InvoiceProducer invoiceProducer;

    @Autowired
    private ImageService imageService;

    @Autowired
    private InvoiceService invoiceService;


    @Override
    public void run(String... args) throws Exception {
//        sendMessagesInLoop();
//        generateAndPublishEmployees(5);
//        counterProducer.sendMessage(100);
//        sendPurchaseRequests();
//        sendPaymentRequests();
//        sendPurchaseRequests2();
//        sendFoodRecords();
//        sendFoodsAndSimpleNumbers();
//        sendGeneratedImages();
//        generateAndSendInvoices();

        sendGeneratedImages2();
    }

    private void generateAndSendInvoices() throws Exception {
        for (int i = 0; i < 10; i++) {
            var invoice = invoiceService.generateInvoice();
            if (i > 5) {
                invoice.setAmount(0);
            }
            invoiceProducer.send(invoice);
        }
    }

    private void sendGeneratedImages2() throws Exception {
        Image image1 = imageService.generateImage("JPG");
        Image image2 = imageService.generateImage("SVG");
        Image image3 = imageService.generateImage("PNG");
        Image image4 = imageService.generateImage("GIF");
        Image image5 = imageService.generateImage("BMP");
        Image image6 = imageService.generateImage("TIFF");

        image2Producer.send(image1, 0);
        image2Producer.send(image2, 0);
        image2Producer.send(image3, 0);

        image2Producer.send(image4, 1);
        image2Producer.send(image5, 1);
        image2Producer.send(image6, 1);
    }

    private void sendGeneratedImages() throws Exception {
        Image image1 = imageService.generateImage("JPG");
        Image image2 = imageService.generateImage("SVG");
        Image image3 = imageService.generateImage("PNG");
        Image image4 = imageService.generateImage("GIF");
        Image image5 = imageService.generateImage("BMP");
        Image image6 = imageService.generateImage("TIFF");

        imageProducer.send(image1, 0);
        imageProducer.send(image2, 0);
        imageProducer.send(image3, 0);

        imageProducer.send(image4, 1);
        imageProducer.send(image5, 1);
        imageProducer.send(image6, 1);
    }

    private void sendFoodsAndSimpleNumbers() throws Exception {
        FoodOrder foodOrder1 = new FoodOrder(11, "Pizza");
        FoodOrder foodOrder2 = new FoodOrder(111, "Bread");
        foodOrderProducer.send(foodOrder1);
        foodOrderProducer.send(foodOrder2);

        SimpleNumber simpleNumber1 = new SimpleNumber(11);
        SimpleNumber simpleNumber2 = new SimpleNumber(12);
        simpleNumberProducer.send(simpleNumber1);
        simpleNumberProducer.send(simpleNumber2);

    }

    private void sendFoodRecords() throws Exception {
        FoodOrder foodOrder1 = new FoodOrder(5, "Pizza");
        FoodOrder foodOrder2 = new FoodOrder(8, "Fish");
        FoodOrder foodOrder3 = new FoodOrder(111, "Bread");

        foodOrderProducer.send(foodOrder1);
        foodOrderProducer.send(foodOrder2);
        foodOrderProducer.send(foodOrder3);
    }

    private void sendPurchaseRequests2() throws Exception {
        PurchaseRequest2 purchaseRequest1 = new PurchaseRequest2(1, "REQ-001", 100, "USD");
        PurchaseRequest2 purchaseRequest2 = new PurchaseRequest2(2, "REQ-002", 200, "EUR");
        PurchaseRequest2 purchaseRequest3 = new PurchaseRequest2(3, "REQ-003", 300, "GBP");

        purchaseRequestProducer2.send(purchaseRequest1);
        purchaseRequestProducer2.send(purchaseRequest2);
        purchaseRequestProducer2.send(purchaseRequest3);

        purchaseRequestProducer2.send(purchaseRequest1);
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
