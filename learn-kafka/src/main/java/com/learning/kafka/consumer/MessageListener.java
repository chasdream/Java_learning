package com.learning.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.EventListener;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/9/12
 */
public interface MessageListener extends EventListener {

    void onMessage(ConsumerRecord<String, Object> record);
}
