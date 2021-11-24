package com.leanring.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *  网络编程，服务端  C/S端
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/19
 */
public class NetServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("服务端启动...");
        while (!ss.isClosed()) {
            Socket request = ss.accept(); // 阻塞方法，等待客户端返回结果
            System.out.println("收到新的连接数： " + request.toString());

            try {
                InputStream is = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String msg; // 接收结果
                while ((msg = reader.readLine()) != null) {
                    if (msg.length() == 0) {
                        break;
                    }
                    System.out.println(msg);
                }

                System.out.println("收到数据，来自：" + request.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                request.close();
            }
        }
    }
}
