package com.leanring.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  网络编程，服务端  C/S端
 *  多线程处理
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/19
 */
public class NetServerHttp {

    // 定义线程池
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Http-服务端启动...");
        while (!ss.isClosed()) {
            Socket request = ss.accept(); // 阻塞方法，等待客户端返回结果
            System.out.println("Http-收到新的连接数： " + request.toString());

            // 多线程处理服务端接收的请求
            threadPool.execute(() -> {
                try {
                    // 接收数据、打印
                    InputStream is = request.getInputStream(); // 获取流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    String msg = null; // 接收结果
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) {
                            break;
                        }
                        System.out.println(msg);
                    }

                    System.out.println("Http-当前线程" + Thread.currentThread().getName() + "收到数据，来自：" + request.toString());

                    // 响应200
                    OutputStream outs = request.getOutputStream();
                    outs.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outs.write("Content-Length: 11\r\n".getBytes());
                    outs.write("\r\n".getBytes()); // 空行
                    outs.write("Hello World".getBytes());
                    outs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        request.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
