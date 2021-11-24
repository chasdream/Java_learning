package com.learning.rpc.remoting;

import java.net.URI;

/**
 * <p>
 *  底层网络传输 - 统一入口（包括服务端和客户端）
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Transporter {

    /**
     * 服务端
     *
     * URI协议 dubbo://127.0.0.1:8080/
     *
     * @param uri 服务器ip、端口
     * @return
     */
    Server start(URI uri, Codec codec, Handler handler);

    /**
     * 消费端，发现服务
     *
     * @param uri
     * @param codec
     * @param handler
     * @return
     */
    Client connection(URI uri, Codec codec, Handler handler);
}
