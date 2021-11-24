package com.learning.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * <p>
 *  kafka生产者
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/9/12
 */
public class ProducerTest {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "120.79.213.115:9092");
        properties.setProperty("beatch.szie", "16384");
        properties.setProperty("linger.ms", "16384");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "1");

        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, Object> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, Object> record = new ProducerRecord<>("test-topic", null, "zhbaewefefe");

        producer.send(record);

        producer.close();
    }
}
