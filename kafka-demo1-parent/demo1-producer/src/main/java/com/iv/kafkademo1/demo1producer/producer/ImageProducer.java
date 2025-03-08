package com.iv.kafkademo1.demo1producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iv.kafkademo1.demo1common.entity.Image;
import com.iv.kafkademo1.demo1producer.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Image image, int partition) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(image);
        kafkaTemplate.send("t-image", partition, image.getType(), json);

        LOG.info("Produced: {}", image);
    }

}
