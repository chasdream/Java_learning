package com.leanring.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * <p>
 *  C/S端
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/19
 */
public class NetClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        OutputStream os = socket.getOutputStream();
        Scanner s = new Scanner(System.in);
        System.out.println("请输入： ");
        String msg = s.nextLine();
        os.write(msg.getBytes("UTF-8"));
        s.close();
        os.close();
    }
}
