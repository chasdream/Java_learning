package com.learning.rpc.rpc.cluster;

import com.learning.rpc.rpc.Invoker;

import java.net.URI;
import java.util.Map;

/**
 * <p>
 *  负载均衡
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public interface LoadBalance {

    /**
     * 负载均衡选择器
     *
     * @param invokerMap
     * @return
     */
    Invoker select(Map<URI, Invoker> invokerMap);
}
