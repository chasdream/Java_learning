package com.learning.demo.actuator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  自定义metrics指标
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/3/8
 */
@Component
public class CustomMetrics {

    private final List<String> lists = new CopyOnWriteArrayList<>();

    public CustomMetrics(MeterRegistry meterRegistry) {
        meterRegistry.gaugeCollectionSize("dictionary.size", Tags.empty(), this.lists);
    }
}
