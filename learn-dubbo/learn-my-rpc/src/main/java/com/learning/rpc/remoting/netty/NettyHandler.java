package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.Handler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p>
 *  处理请求内容
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class NettyHandler extends ChannelDuplexHandler {

    private Handler handler;

    public NettyHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 入栈事件
     * 收到数据 请求/响应
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handler.onReceive(new NettyChannel(ctx.channel()), msg);
    }
}
