package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.RPCChannel;
import io.netty.channel.Channel;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class NettyChannel implements RPCChannel {

    private Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(byte[] message) {
        channel.writeAndFlush(message);
    }
}
