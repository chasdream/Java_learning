package com.learning.rpc.remoting.netty;

import com.learning.rpc.remoting.Codec;
import com.learning.rpc.remoting.Handler;
import com.learning.rpc.remoting.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * <p>
 *  netty网络服务
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class NettyServer implements Server {

    // 开启一个网络服务
    // 创建事件循环组
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    @Override
    public void start(URI uri, Codec codec, Handler handler) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class) // 指定所使用的nio传输channel
                    // 指定要监听的地址
                    .localAddress(new InetSocketAddress(uri.getHost(), uri.getPort()))
                    // 添加handler  有连接之后的处理逻辑
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 协议编解码器
                            socketChannel.pipeline().addLast(new NettyCodec(codec.createInstance()));
                            // 执行具体的逻辑
                            socketChannel.pipeline().addLast(new NettyHandler(handler));
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("完成端口绑定和服务启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
