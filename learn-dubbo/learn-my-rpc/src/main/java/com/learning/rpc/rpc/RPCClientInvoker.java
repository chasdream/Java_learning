package com.learning.rpc.rpc;

import com.learning.rpc.common.serialize.Serialization;
import com.learning.rpc.common.tools.SpiUtils;
import com.learning.rpc.remoting.Client;
import com.learning.rpc.rpc.protocol.rpc.handler.RPCClientHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class RPCClientInvoker implements Invoker {

    Client client;

    Serialization serialization;

    public RPCClientInvoker(Client client, Serialization serialization) {
        this.client = client;
        this.serialization = serialization;
    }

    @Override
    public Class<?> getInterface() {
        return null;
    }

    @Override
    public Object invoke(RPCInvocation rpcInvocation) throws Exception {

        // 序列化 rpcInvocation对象，发送到服务提供者，服务提供者配置决定
        // 向远程发起调用，向提供者发送数据
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl("JSONSerialization", Serialization.class);
        byte[] requestBody = serialization.serialize(rpcInvocation);

        // 发起请求，基于netty长连接  rpcInvocation对象 协议数据包 编码
        this.client.getChannel().send(requestBody);

        // 另外一个线程 接收响应，解码完成之后的Handler(RPCClientHandler)
        // 实现等待结果的过程
        CompletableFuture<?> future = RPCClientHandler.waitResult(rpcInvocation.getId());
        // 获取结果，如果没有结果就等待
        Object result = future.get(60, TimeUnit.SECONDS);
        Response response = (Response) result;
        if (response.getStatus() == 200) {
            return response.getContent();
        } else {
            throw new Exception("server error: " + response.getContent().toString());
        }
    }
}
