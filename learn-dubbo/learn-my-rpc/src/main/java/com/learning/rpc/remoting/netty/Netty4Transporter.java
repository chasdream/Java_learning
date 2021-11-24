package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.*;

import java.net.URI;

/**
 * <p>
 *  netty 网络请求处理
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class Netty4Transporter implements Transporter {

    /**
     * 启动netty
     *
     * @param uri 服务器ip、端口
     * @return
     */
    @Override
    public Server start(URI uri, Codec codec, Handler handler) {
        NettyServer server = new NettyServer();
        server.start(uri, codec, handler);
        return server;
    }

    @Override
    public Client connection(URI uri, Codec codec, Handler handler) {
        NettyClient client = new NettyClient();
        client.connect(uri, codec, handler);
        return client;
    }
}
