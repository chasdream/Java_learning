package com.leraning.rpc.remoting;

import com.learning.rpc.remoting.Codec;
import com.learning.rpc.remoting.Handler;
import com.learning.rpc.remoting.RPCChannel;
import com.learning.rpc.remoting.netty.Netty4Transporter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  测试方法：curl http://127.0.0.1:8088/
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class Netty4TransporterTest {

    public static void main(String[] args) throws URISyntaxException {

        new Netty4Transporter().start(new URI("rpc://127.0.0.1:8088"), new Codec() {
            @Override
            public byte[] encode(Object msg) throws Exception {
                return new byte[0];
            }

            @Override
            public List<Object> decode(byte[] message) throws Exception {
                System.out.println("请求内容：" + (new String(message)));
                // 解码
                List<Object> objects = new ArrayList<>();
                objects.add("1:" + new String(message));
                objects.add("2:" + new String(message));
                objects.add("3:" + new String(message));
                return objects;
            }

            @Override
            public Codec createInstance() {
                return this;
            }
        }, new Handler() {
            @Override
            public void onReceive(RPCChannel rpcChannel, Object message) throws Exception {
                System.out.println(message);
            }

            @Override
            public void onWrite(RPCChannel rpcChannel, Object message) throws Exception {

            }
        });

    }

}
