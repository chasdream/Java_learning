package com.learning.rpc.rpc.protocol.rpc.handler;

import com.learning.rpc.remoting.Handler;
import com.learning.rpc.remoting.RPCChannel;
import com.learning.rpc.rpc.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  客户端处理器
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class RPCClientHandler implements Handler {

    private static final Map<Long, CompletableFuture<Response>> invokerMap = new ConcurrentHashMap<>();

    /**
     * 利用Future机制等待响应结果处理
     * 登记一下响应结果，结果会创建并返回一个Future，每一个等待结果的线程单独一个Future
     *
     * @param messageId
     * @return
     */
    public static CompletableFuture<Response> waitResult(long messageId) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        invokerMap.put(messageId, future);
        return future;
    }

    /**
     * 客户端，收到响应数据，方法执行的返回值
     *
     * netty中网络框架线程
     *
     * @param rpcChannel
     * @param message
     * @throws Exception
     */
    @Override
    public void onReceive(RPCChannel rpcChannel, Object message) throws Exception {
        Response response = (Response) message;
        // 接收所有的结果 和 invoker调用者不在一个线程
        // 根据ID 和具体的请求对应起来
        invokerMap.get(response.getRequestId()).complete(response);
    }

    @Override
    public void onWrite(RPCChannel rpcChannel, Object message) throws Exception {

    }
}
