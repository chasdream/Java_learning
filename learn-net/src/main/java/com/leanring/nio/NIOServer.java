package com.leanring.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/19
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建网络服务端
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);// 设置为非阻塞模式
        // 绑定端口
    }
}
