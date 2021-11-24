package com.learning.rpc.rpc.cluster.loadbalance;

import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.cluster.LoadBalance;

import java.net.URI;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  随机选择
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public Invoker select(Map<URI, Invoker> invokerMap) {
        int index = new Random().nextInt(invokerMap.values().size());
        return invokerMap.values().toArray(new Invoker[]{})[index];
    }
}
