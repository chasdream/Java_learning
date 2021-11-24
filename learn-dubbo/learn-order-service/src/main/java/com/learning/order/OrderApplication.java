package com.learning.order;

import com.learning.order.service.OrderService;
import com.learning.rpc.config.spring.annotation.EnableRPC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
@Configuration
@ComponentScan("com.learning.order")
@PropertySource("classpath:/rpc.properties")
@EnableRPC
public class OrderApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderApplication.class);
        context.start();

        System.out.println("order 服务启动成功....");

        OrderService orderService = context.getBean(OrderService.class);
        orderService.create("订单创建成功");

        // 服务阻塞x
        System.in.read();
        context.close();

    }
}
