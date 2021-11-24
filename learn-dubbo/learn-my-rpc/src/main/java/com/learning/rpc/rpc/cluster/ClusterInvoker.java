package com.learning.rpc.rpc.cluster;

import com.learning.rpc.common.tools.SpiUtils;
import com.learning.rpc.config.ReferenceConfig;
import com.learning.rpc.config.RegistryConfig;
import com.learning.rpc.registry.NotifyListener;
import com.learning.rpc.registry.RegistryService;
import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;
import com.learning.rpc.rpc.cluster.loadbalance.RandomLoadBalance;
import com.learning.rpc.rpc.protocol.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  集群环境下的Invoker，包含一个服务的多个实例的调用
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class ClusterInvoker implements Invoker {

    ReferenceConfig referenceConfig;

    LoadBalance loadBalance;

    /**
     * 存储这个服务能够调用的所有实例
     */
    private final Map<URI, Invoker> invokers = new ConcurrentHashMap<>();

    public ClusterInvoker(ReferenceConfig referenceConfig) throws URISyntaxException {
        this.referenceConfig = referenceConfig;
        loadBalance = (LoadBalance) SpiUtils.getServiceImpl(referenceConfig.getLoadBalance(), LoadBalance.class);
        // 接口的全类名
        String serviceName = referenceConfig.getService().getName();
        // 服务发现  -- 注册中心
        // 遍历注册中心
        for (RegistryConfig registryConfig : referenceConfig.getRegistryConfigs()) {
            URI registryUri = new URI(registryConfig.getAddress());
            RegistryService registryService =
                    (RegistryService) SpiUtils.getServiceImpl(registryUri.getScheme(), RegistryService.class);
            registryService.init(registryUri);
            registryService.subscribe(serviceName, new NotifyListener() {
                /**
                 * 当服务有更新的适合触发新增或剔除
                 *
                 * @param uris
                 */
                @Override
                public void notify(Set<URI> uris) {
                    System.out.println("当前服务更新前的invoker信息：" + invokers);
                    // 剔除 创建好的Invoker，是不是存在于最新的实例里面
                    for (URI uri : invokers.keySet()) {
                        if (!uris.contains(uri)) {
                            invokers.remove(uri);
                        }
                    }

                    // 新增一个invoker(新建invoker)
                    for (URI uri : uris) {
                        if (!invokers.containsKey(uri)) {
                            // 有一个服务实例
                            Protocol protocol = (Protocol) SpiUtils.getServiceImpl(uri.getScheme(), Protocol.class);
                            // invoker 代表一个长连接
                            Invoker invoker = protocol.refer(uri);
                            // 保存Invoker实例
                            invokers.putIfAbsent(uri, invoker);
                        }
                    }
                    System.out.println("当前服务更新后的invoker信息：" + invokers);
                }
            });
        }

    }

    /**
     *
     * @return proxy + invoker = 完整的代理
     */
    @Override
    public Class<?> getInterface() {
        return referenceConfig.getService();
    }

    @Override
    public Object invoke(RPCInvocation rpcInvocation) throws Exception {
        // 调用一次invoker  调用哪个invoker? 负载均衡
        Invoker select = loadBalance.select(invokers);
        Object result = select.invoke(rpcInvocation);
        return result;
    }
}
