package com.learning.springboot;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/12
 */
@Configuration
@ComponentScan("com.learning.springboot")// 扫描com.learning包,去掉一部分bean xml定义
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    /**
     * 返回结果类型转换，转成json格式
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        converters.add(jsonHttpMessageConverter);
    }
}
