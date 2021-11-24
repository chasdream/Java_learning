package com.leraning.rpc.protocol;

import com.learning.rpc.common.serialize.json.JSONSerialization;
import com.learning.rpc.remoting.netty.Netty4Transporter;
import com.learning.rpc.rpc.RPCInvocation;
import com.learning.rpc.rpc.protocol.rpc.codec.RPCCodec;
import com.learning.rpc.rpc.protocol.rpc.handler.RPCServerHandler;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>
 *  服务提供者测试：集成了rpc协议处理器
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCProtocolTransporterTest {

    public static void main(String[] args) throws URISyntaxException {
        RPCCodec rpcCodec = new RPCCodec();
        rpcCodec.setDecodeType(RPCInvocation.class);
        rpcCodec.setSerialization(new JSONSerialization());
        new Netty4Transporter().start(new URI("rpc://127.0.0.1:8088"), rpcCodec, new RPCServerHandler());
    }
}
