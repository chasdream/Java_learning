package com.learning.rpc.remoting;

import java.net.URI;

/**
 * <p>
 *  连接客户端，和服务端进行连接
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public interface Client {

    /**
     * 客户端连接服务器
     *
     * @param uri 指定连接的uri
     * @param codec 指定连接的编解码方式
     * @param handler 指定连接的处理器
     */
    void connect(URI uri, Codec codec, Handler handler);

    /**
     *
     * @return
     */
    RPCChannel getChannel();
}
