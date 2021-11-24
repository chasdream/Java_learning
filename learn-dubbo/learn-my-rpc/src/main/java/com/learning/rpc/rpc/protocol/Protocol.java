package com.learning.rpc.rpc.protocol;

import com.learning.rpc.rpc.Invoker;

import java.net.URI;

/**
 * <p>
 *  协议定义接口
 *
 *  需要定义spi的实现
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Protocol {

    /**
     * 开放服务
     *
     * @param exportUri 协议名称: //ip:port/service全类名?参数名称=参数值
     * @param invoker 调用具体实现类的代理对象
     */
    void export(URI exportUri, Invoker invoker);

    /**
     * 获取一个协议所对应的invoker，用于调用
     * @param consumerUri
     * @return
     */
    Invoker refer(URI consumerUri);
}
