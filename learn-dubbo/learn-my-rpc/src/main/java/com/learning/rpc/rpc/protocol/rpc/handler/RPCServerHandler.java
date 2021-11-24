package com.learning.rpc.rpc.protocol.rpc.handler;

import com.learning.rpc.common.serialize.Serialization;
import com.learning.rpc.remoting.Handler;
import com.learning.rpc.remoting.RPCChannel;
import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;
import com.learning.rpc.rpc.Response;

/**
 * <p>
 *  服务端处理器
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCServerHandler implements Handler {

    /**
     * 收到数据
     *
     * @param rpcChannel
     * @param message 指RPCInvocation对象
     * @throws Exception
     */
    @Override
    public void onReceive(RPCChannel rpcChannel, Object message) throws Exception {
        RPCInvocation rpcInvocation = (RPCInvocation) message;
        System.out.println("收到rpcInvocation信息：" + rpcInvocation);

        // 发出数据给客户端 -- response对象
        Response response = new Response();
        try {
            // 发起方法调用请求 -- 通过@RPCService 标记的对象
            Object result = getInvoker().invoke(rpcInvocation);
            System.out.println("服务端执行的结果：" + result);
            response.setRequestId(rpcInvocation.getId());
            response.setStatus(200);
            response.setContent(result);
        } catch (Exception e) {
            response.setStatus(100);
            response.setContent(e.getMessage());
            e.printStackTrace();
        }

        // 编码 -- 编码器进行发送的数据进行编码，将对象转成序列化对象
        byte[] responseBody = getSerialization().serialize(response);
        // 发送消息，触发write()
        rpcChannel.send(responseBody);
    }

    @Override
    public void onWrite(RPCChannel rpcChannel, Object message) throws Exception {

    }

    private Invoker invoker;

    private Serialization serialization;

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }
}
