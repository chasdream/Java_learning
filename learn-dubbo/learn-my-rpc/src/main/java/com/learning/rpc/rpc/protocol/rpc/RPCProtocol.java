package com.learning.rpc.rpc.protocol.rpc;

import com.learning.rpc.common.serialize.Serialization;
import com.learning.rpc.common.tools.SpiUtils;
import com.learning.rpc.common.tools.URIUtils;
import com.learning.rpc.remoting.Client;
import com.learning.rpc.remoting.Transporter;
import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;
import com.learning.rpc.rpc.RPCClientInvoker;
import com.learning.rpc.rpc.Response;
import com.learning.rpc.rpc.protocol.Protocol;
import com.learning.rpc.rpc.protocol.rpc.codec.RPCCodec;
import com.learning.rpc.rpc.protocol.rpc.handler.RPCClientHandler;
import com.learning.rpc.rpc.protocol.rpc.handler.RPCServerHandler;

import java.net.URI;

/**
 * <p>
 *  自定义协议具体实现
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCProtocol implements Protocol {

    /**
     * 完成协议的启动
     *
     * @param exportUri 协议名称: //ip:port/service全类名?参数名称=参数值
     */
    @Override
    public void export(URI exportUri, Invoker invoker) {
        // url中解析序列化方式
        String serializationName = URIUtils.getParam(exportUri, "serialization");
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl(serializationName, Serialization.class);

        // 定义编解码对象
        RPCCodec rpcCodec = new RPCCodec();
        rpcCodec.setDecodeType(RPCInvocation.class);
        rpcCodec.setSerialization(serialization);

        // 创建handler，收到请求处理器
        RPCServerHandler rpcServerHandler = new RPCServerHandler();
        rpcServerHandler.setInvoker(invoker);
        // 构建序列化
        rpcServerHandler.setSerialization(serialization);

        // 底层网络框架
        String transporterName = URIUtils.getParam(exportUri, "transporter");
        Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);
        // 启动服务
        transporter.start(exportUri, rpcCodec, rpcServerHandler);
    }

    @Override
    public Invoker refer(URI consumerUri) {
        // 获取客户端连接
        // 序列化
        String serializationName = URIUtils.getParam(consumerUri, "serialization");
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl(serializationName, Serialization.class);

        // 编解码器
        RPCCodec rpcCodec = new RPCCodec();
        rpcCodec.setDecodeType(Response.class); // 客户端解码服务端发送过来的响应
        rpcCodec.setSerialization(serialization);

        // 收到响应之后的处理器
        RPCClientHandler rpcClientHandler = new RPCClientHandler();

        // 底层网络框架 建立长连接
        String transporterName = URIUtils.getParam(consumerUri, "transporter");
        Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);
        Client connection = transporter.connection(consumerUri, rpcCodec, rpcClientHandler);

        // 创建一个 RPCInvoker 通过网络连接发送数据
        RPCClientInvoker rpcClientInvoker = new RPCClientInvoker(connection, serialization);
        return rpcClientInvoker;
    }
}
