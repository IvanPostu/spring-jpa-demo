package com.iv.demo2.kafka.stream.stream.inventory.util;

import com.iv.kafkademo2common.broker.message.InventoryMessage;
import com.iv.kafkademo2common.broker.message.util.LocalDateTimeUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class InventoryTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var inventoryMessage = (InventoryMessage) record.value();

        return inventoryMessage != null ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime())
                : record.timestamp();
    }

}
