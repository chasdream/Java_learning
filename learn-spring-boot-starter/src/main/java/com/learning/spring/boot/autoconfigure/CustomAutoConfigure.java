package com.learning.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *  自定义自动装配类
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/3/7
 */
@Configuration
@EnableConfigurationProperties(CustomProperties.class)
public class CustomAutoConfigure {

    @Bean
    public CustomBean getCustomBean(CustomProperties properties) {
        CustomBean customBean = new CustomBean();
        customBean.setFileName(properties.getFileName());
        customBean.setFileSize(properties.getFileSize());
        return customBean;
    }
}
