package com.learning.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * <p>
 * kafka消费者
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/9/12
 */
public class ConsumerTest {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "120.79.213.115:9092");
        properties.setProperty("group.id", "consumer-test");
        properties.setProperty("enable.auto.commit", "true"); // 自动提交
        properties.setProperty("auto.commit.interval", "1000");

        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, Object> consumer = new KafkaConsumer<>(properties);

        Collection<String> col = new ArrayList<>();
        col.add("test-topic");

        consumer.subscribe(col, new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("onPartitionsRevoked partitions: " + JSON.toJSONString(partitions));
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("onPartitionsAssigned partitions: " + JSON.toJSONString(partitions));
            }
        });

        while (true) {
            ConsumerRecords<String, Object> poll = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Object> record : poll.records("test-topic")) {
                System.err.println("record.key: " + record.key()
                        + "; record.value: " + JSON.toJSONString(record.value())
                        + "; record.offset: " + record.offset()
                        + "; record.topic: " + record.topic()
                        + "; record.partition: " + record.partition()
                        + "; record.timestamp" + record.timestamp());
            }
        }

    }
}
