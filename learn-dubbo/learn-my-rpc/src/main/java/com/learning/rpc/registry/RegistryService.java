package com.learning.rpc.registry;

import java.net.URI;

/**
 * <p>
 *  服务注册顶层接口设计
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public interface RegistryService {

    /**
     * 注册
     *
     * @param uri
     */
    void register(URI uri);

    /**
     * 订阅指定的服务
     *
     * @param service 注册的服务名
     * @param notifyListener 监听接口
     */
    void subscribe(String service, NotifyListener notifyListener);

    /**
     * 初始化
     *
     * @param address uri地址
     */
    void init(URI address);

}
