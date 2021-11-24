package com.leanring.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p>
 *  server启动过程中的回调
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/9/4
 */
public class ServerHandler implements ChannelHandler {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelHandler.handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelHandler.handlerRemoved");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ChannelHandler.exceptionCaught");
    }
}
