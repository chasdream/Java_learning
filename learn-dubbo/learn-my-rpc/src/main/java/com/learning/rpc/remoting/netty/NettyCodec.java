package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.List;

/**
 * <p>
 *  进行编解码器，定义，不做具体的编解码协议实现
 *
 *  1. 接收端：将请求中的网络字节数据转成Java对象
 *  2. 发送端：将发送的数据转成特定的协议格式进行发送
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class NettyCodec extends ChannelDuplexHandler {

    private Codec codec;

    public NettyCodec(Codec codec) {
        this.codec = codec;
    }

    /**
     * 入栈事件，收到数据请求或响应
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 解码
        // 读取数据
        ByteBuf data = (ByteBuf) msg;
        byte[] dataBytes = new byte[data.readableBytes()];
        data.readBytes(dataBytes);

        // 格式转
        List<Object> out = codec.decode(dataBytes);

        // 处理器继续处理
        for (Object o : out) {
            ctx.fireChannelRead(o);
        }
    }

    /**
     * 出栈事件
     *
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        byte[] encode = codec.encode(msg);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(encode);
        super.write(ctx, byteBuf, promise);
    }
}
