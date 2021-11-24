package com.leanring.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/9/1
 */
public class NettyServer {

    //
    static NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //
    static NioEventLoopGroup workGroup = new NioEventLoopGroup();

    public static void main(String[] args) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class) // 设置服务端的SocketChannel
                    .childOption(ChannelOption.TCP_NODELAY, true) // 给创建客户端的链接设置TCP基本属性
                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue") // 每次创建客户端链接时绑定基本的属性
                    .handler(new ServerHandler()) // 服务端启动过程中需要处理哪些逻辑，如accept链接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 数据流读写处理逻辑，如编解码、消息处理等
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("netty server: " + socketChannel.toString());
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 编码
                            pipeline.addLast(new StringEncoder(Charset.defaultCharset()));
                            // 解码
                            pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
                            // 消息处理器
                            pipeline.addLast(new SimpleChannelInboundHandler<String>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.bind(8088).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
            // 优雅停机
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
