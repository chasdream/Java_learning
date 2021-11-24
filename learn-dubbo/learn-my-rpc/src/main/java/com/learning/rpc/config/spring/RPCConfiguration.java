package com.learning.rpc.config.spring;

import com.learning.rpc.config.ProtocolConfig;
import com.learning.rpc.config.RegistryConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;

/**
 * <p>
 * 将自定义对象 放到spring容器管理  通过BeanDefinition
 * 在Spring启动早期进行配置文件处理
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCConfiguration implements ImportBeanDefinitionRegistrar {

    private StandardEnvironment environment;

    private RPCConfiguration(Environment environment) {
        this.environment = (StandardEnvironment) environment;
    }

    /**
     * 让spring启动时，装置没有注解或配置文件中配置的bean对象
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        System.out.println("装配配置文件信息....");

        // 构建全新的BeanDefinition
        // 告诉spring 可以完成配置对象的加载
        BeanDefinitionBuilder builder = null;

        // 加载ProtocolConfig
        builder = BeanDefinitionBuilder.genericBeanDefinition(ProtocolConfig.class);
        // 定义ProtocolConfig中属性的值
        // 遍历ProtocolConfig属性的值
        for (Field field : ProtocolConfig.class.getDeclaredFields()) {
            // 从配置文件中找到属性所匹配的配置值
            String value = environment.getProperty("rpc.protocol." + field.getName());
            builder.addPropertyValue(field.getName(), value);
        }
        // 将beanDefinition注册到BeanDefinitionRegistry
        registry.registerBeanDefinition("protocolConfig", builder.getBeanDefinition());

        // 加载 RegistryConfig
        builder = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfig.class);
        for (Field field : RegistryConfig.class.getDeclaredFields()) {
            String value = environment.getProperty("rpc.registry." + field.getName());
            builder.addPropertyValue(field.getName(), value);
        }
        registry.registerBeanDefinition("registryConfig", builder.getBeanDefinition());

    }
}
