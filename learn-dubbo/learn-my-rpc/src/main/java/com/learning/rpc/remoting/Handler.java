package com.learning.rpc.remoting;

/**
 * <p>
 *  具体的协议实现
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Handler {

    void onReceive(RPCChannel rpcChannel, Object message) throws Exception;

    void onWrite(RPCChannel rpcChannel, Object message) throws Exception;
}
