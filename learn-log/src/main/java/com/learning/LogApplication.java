package com.learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
@SpringBootApplication
public class LogApplication {
    
    private static Logger logger = LoggerFactory.getLogger(LogApplication.class);

    public static void main(String[] args) {
        // logback将定义的参数放到日志文件中
//        MDC.put("userId", "1230192");
        SpringApplication.run(LogApplication.class);

        logger.info("启动成功");
    }
}
