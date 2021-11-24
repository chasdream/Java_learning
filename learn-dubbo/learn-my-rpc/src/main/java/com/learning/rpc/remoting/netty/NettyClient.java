package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.Client;
import com.learning.rpc.remoting.Codec;
import com.learning.rpc.remoting.Handler;
import com.learning.rpc.remoting.RPCChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.URI;

/**
 * <p>
 * netty客户端连接
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class NettyClient implements Client {

    private RPCChannel channel = null;

    private EventLoopGroup group = null;

    @Override
    public void connect(URI uri, Codec codec, Handler handler) {
        try {

            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            // 指定事件循环组
            bootstrap.group(group)
                    // 指定使用的nio传输channel
                    .channel(NioSocketChannel.class)
                    // 添加一个handler
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            // 指定编解码方式
                            channel.pipeline().addLast(new NettyCodec(codec));
                            // 指定处理器
                            channel.pipeline().addLast(new NettyHandler(handler));
                        }
                    });
            // 同步创建连接
            ChannelFuture channelFuture = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            channel = new NettyChannel(channelFuture.channel());

            // 优雅停机 kill pid 响应退出信号
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("我要停机了");
                        synchronized (NettyServer.class) {
                            // 关闭线程组
                            group.shutdownGracefully().sync();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RPCChannel getChannel() {
        return channel;
    }
}
