package com.learning.sms;

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
 * @since 2021/8/21
 */
@Configuration
@ComponentScan("com.learning.sms")
@PropertySource("classpath:/rpc.properties")
@EnableRPC
public class SmsApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext acx = new AnnotationConfigApplicationContext(SmsApplication.class);
        acx.start();

        System.out.println("应用启动....");

        // 阻塞不退出
        System.in.read();
        acx.close();

    }
}
