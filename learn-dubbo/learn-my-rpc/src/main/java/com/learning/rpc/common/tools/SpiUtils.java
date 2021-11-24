package com.learning.rpc.common.tools;

import java.util.ServiceLoader;

/**
 * <p>
 *  spi工具类
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class SpiUtils {

    /**
     * spi机制，解析meta-inf配置文件的实现接口
     *
     * @param serviceName
     * @param classType
     * @return
     */
    public static Object getServiceImpl(String serviceName, Class classType) {
        ServiceLoader services = ServiceLoader.load(classType, Thread.currentThread().getContextClassLoader());
        // 根据服务定义的协议，依次暴露。如果有多个协议就暴露多次
        for (Object obj : services) {
            if (obj.getClass().getSimpleName().equals(serviceName)) {
                return obj;
            }
        }
        return null;
    }
}
