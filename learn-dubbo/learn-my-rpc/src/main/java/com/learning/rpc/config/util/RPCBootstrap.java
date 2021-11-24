package com.learning.rpc.config.util;

import com.learning.rpc.common.tools.SpiUtils;
import com.learning.rpc.config.ProtocolConfig;
import com.learning.rpc.config.ReferenceConfig;
import com.learning.rpc.config.RegistryConfig;
import com.learning.rpc.config.ServiceConfig;
import com.learning.rpc.registry.RegistryService;
import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCClientInvoker;
import com.learning.rpc.rpc.cluster.ClusterInvoker;
import com.learning.rpc.rpc.protocol.Protocol;
import com.learning.rpc.rpc.protocol.rpc.RPCProtocol;
import com.learning.rpc.rpc.proxy.ProxyFactory;

import java.net.NetworkInterface;
import java.net.URI;

/**
 * <p>
 * 服务启动引导类
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCBootstrap {

    /**
     * 服务提供者：暴露Service服务
     * <p>
     * 根据协议名称找到具体的协议实现（可以实现多个协议）
     *
     * @param serviceConfig serviceConfig
     */
    public static void export(ServiceConfig serviceConfig) {

        // 构建代理对象，代理的对象是ServiceBean
        Invoker invoker = ProxyFactory.getInvoker(serviceConfig.getReference(), serviceConfig.getService());

        try {
            // invoker对象
            // 根据服务定义的协议，依次暴露，如果有多个协议就暴露多次
            for (ProtocolConfig protocolConfig : serviceConfig.getProtocolConfigs()) {
                // 组织URL  协议://ip:port/service全类名?配置名=值&配置名2=值
                StringBuilder sb = new StringBuilder();
                sb.append(protocolConfig.getName()).append("://");
                // 选择具体网卡设备
                String hostAddress = NetworkInterface
                        .getNetworkInterfaces().nextElement().getInterfaceAddresses().get(0).getAddress().getHostName();
//                sb.append(hostAddress).append(":");
                sb.append("127.0.0.1").append(":");
                sb.append(protocolConfig.getPort()).append("/");
                sb.append(serviceConfig.getService().getName()).append("?");
                // 版本号定义，待定义
                sb.append("transporter=").append(protocolConfig.getTransporter());
                sb.append("&serialization=").append(protocolConfig.getSerialization());

                URI exportUri = new URI(sb.toString());
                System.out.println("准备暴露服务：" + exportUri);

                // 通过协议名称创建服务   -- todo 需要考虑多服务多次注册的情况
                Protocol protocol = (Protocol) SpiUtils.getServiceImpl(protocolConfig.getName(), Protocol.class);
                protocol.export(exportUri, invoker);

                // 将服务注册到注册中心
                for (RegistryConfig registryConfig : serviceConfig.getRegistryConfigs()) {
                    URI registryUri = new URI(registryConfig.getAddress());
                    RegistryService registryService =
                            (RegistryService) SpiUtils.getServiceImpl(registryUri.getScheme(), RegistryService.class);
                    // 初始化注册
                    registryService.init(registryUri);
                    // 将服务提供者的service uri进行注册
                    registryService.register(exportUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个代理对象
     *
     * 服务消费者：
     * 将服务消费者注册到服务注册中心
     * 暴露服务消费者
     *
     * @param referenceConfig referenceConfig
     * @return
     */
    public static Object getReferenceBean(ReferenceConfig referenceConfig) {
        try {

            // 创建Invoker对象，用于访问远程接口的真实对象 (被代理的对象)
//            RPCProtocol rpcProtocol = new RPCProtocol();
//            Invoker invoker = rpcProtocol.refer(new URI("rpc-RPCProtocol://127.0.0.1:8088/com.learning.sms.service.SmsService?transporter=Netty4Transporter&serialization=JSONSerialization"));


            // 集群环境下 根据服务注册中心获取到服务提供者实例
            ClusterInvoker clusterInvoker = new ClusterInvoker(referenceConfig);
            // 代理对象
            Object proxy = ProxyFactory.getProxy(clusterInvoker, new Class[]{referenceConfig.getService()});
            return proxy;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
