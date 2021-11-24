package com.leraning.rpc.protocol;

import com.learning.rpc.common.serialize.Serialization;
import com.learning.rpc.common.tools.ByteUtils;
import com.learning.rpc.common.tools.SpiUtils;
import com.learning.rpc.rpc.RPCInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <p>
 * 模拟远程调用
 * <p>
 * TRPCProtocol： 0xdabb（两字节） + body长度(4字节) + body（默认json序列化）
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class ClientMock {

    public static void main(String[] args) throws Exception {

        // 构建body
        RPCInvocation rpcInvocation = new RPCInvocation();
        rpcInvocation.setServiceName("com.learning.sms.service.SmsService");
        rpcInvocation.setMethodName("send");
        rpcInvocation.setParameterTypes(new Class[]{String.class, String.class});
        rpcInvocation.setArguments(new Object[]{"10010", "模拟远程调用测试"});
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl("JSONSerialization", Serialization.class);
        byte[] requestBody = serialization.serialize(rpcInvocation);

        // 构建header(协议头)
        ByteBuf requestByteBuf = Unpooled.buffer();
        requestByteBuf.writeByte(0xda);
        requestByteBuf.writeByte(0xbb);
        requestByteBuf.writeBytes(ByteUtils.int2bytes(requestBody.length));
        requestByteBuf.writeBytes(requestBody);

        // 发起请求
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1", 8088));
        client.write(ByteBuffer.wrap(requestByteBuf.array()));

        // 接收响应
        ByteBuffer response = ByteBuffer.allocate(1024);
        client.read(response);
        System.out.println("响应内容：");
        System.out.println(new String(response.array()));
    }
}
