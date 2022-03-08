package com.learning.demo.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * <p>
 *  自定义健康检查项
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/3/8
 */
@Component
public class CustomHealthCheck extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int i = new Random().nextInt();
        if (i % 2 == 0) {
            builder.withDetail("类型：", "偶数").up();
        } else {
            builder.withDetail("类型：", "奇数").up();
        }
    }
}
