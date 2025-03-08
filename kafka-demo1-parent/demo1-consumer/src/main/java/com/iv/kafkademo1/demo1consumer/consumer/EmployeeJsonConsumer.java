package com.iv.kafkademo1.demo1consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

//@Service
public class EmployeeJsonConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(groupId = "default-spring-consumer", concurrency = "1", topicPartitions = {
            @TopicPartition(topic = "t-employee", partitions = {"0"})
    })
    public void listen(String message) throws JsonMappingException, JsonProcessingException {
        var employee = objectMapper.readValue(message, Employee.class);
        LOG.info("Employee consumed : {}", employee);
    }

}
