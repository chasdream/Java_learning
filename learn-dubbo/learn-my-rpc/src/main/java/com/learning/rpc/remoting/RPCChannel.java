package com.learning.rpc.remoting;

/**
 * <p>
 *  此对象代表一个客户端连接
 *  此处封装的目的是因为不同的底层网络框架，该连接的定义不同
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface RPCChannel {

    /**
     * 不用关注协议
     *
     * @param message 发送业务数据即可
     */
    void send(byte[] message);
}
