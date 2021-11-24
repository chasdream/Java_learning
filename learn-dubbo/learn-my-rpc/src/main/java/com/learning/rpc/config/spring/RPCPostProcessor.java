package com.learning.rpc.config.spring;

import com.learning.rpc.config.ProtocolConfig;
import com.learning.rpc.config.ReferenceConfig;
import com.learning.rpc.config.RegistryConfig;
import com.learning.rpc.config.ServiceConfig;
import com.learning.rpc.config.annotation.RPCReference;
import com.learning.rpc.config.annotation.RPCService;
import com.learning.rpc.config.util.RPCBootstrap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;

/**
 * <p>
 *     服务提供者相关配置
 *     spring扫描 初始化对象之后，找到RPCService注解
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCPostProcessor implements ApplicationContextAware, InstantiationAwareBeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * bean 其他拓展
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 服务提供者
        if (bean.getClass().isAnnotationPresent(RPCService.class)) {

            System.out.println("找到了需要开发网络访问的service实现类，启动网络服务。构建ServiceConfig配置");

            ServiceConfig serviceConfig = new ServiceConfig();
            serviceConfig.addProtocolConfig(applicationContext.getBean(ProtocolConfig.class)); // 获取通讯协议信息
            serviceConfig.addRegistryConfig(applicationContext.getBean(RegistryConfig.class));
            serviceConfig.setReference(bean);
            RPCService rpcService = bean.getClass().getAnnotation(RPCService.class);
            // 如果value=""，取第一个
            if (void.class == rpcService.interfaceClass()) {
                serviceConfig.setService(bean.getClass().getInterfaces()[0]);
            } else {
                serviceConfig.setService(rpcService.interfaceClass());
            }

            // 引导服务启动
            RPCBootstrap.export(serviceConfig);
        }

        // 服务消费者 注入Spring容器
        for (Field declaredField : bean.getClass().getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(RPCReference.class)) {
                continue;
            }
            // @RPCReference注解引用相关配置，保存到ReferenceConfig对象中
            ReferenceConfig referenceConfig = new ReferenceConfig();
            referenceConfig.addRegistryConfigs(applicationContext.getBean(RegistryConfig.class));
            referenceConfig.addProtocolConfigs(applicationContext.getBean(ProtocolConfig.class));
            referenceConfig.setService(declaredField.getType());
            referenceConfig.setLoadBalance(declaredField.getAnnotation(RPCReference.class).loadBalance());

            Object referenceBean = RPCBootstrap.getReferenceBean(referenceConfig);
            try {
                declaredField.setAccessible(true);
                declaredField.set(bean, referenceBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return bean;
    }
}
