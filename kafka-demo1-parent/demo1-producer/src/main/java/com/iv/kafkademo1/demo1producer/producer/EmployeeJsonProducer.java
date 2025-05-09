package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Employee employee) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(employee);
        kafkaTemplate.send("t-employee", json);
    }

}
