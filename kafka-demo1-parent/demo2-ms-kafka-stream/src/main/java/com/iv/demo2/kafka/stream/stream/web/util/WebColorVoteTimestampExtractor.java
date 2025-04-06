package com.iv.demo2.kafka.stream.stream.web.util;

import com.iv.kafkademo2common.broker.message.WebColorVoteMessage;
import com.iv.kafkademo2common.broker.message.util.LocalDateTimeUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var message = (WebColorVoteMessage) record.value();

        return message != null ? LocalDateTimeUtil.toEpochTimestamp(message.getVoteDateTime()) : record.timestamp();
    }

}
