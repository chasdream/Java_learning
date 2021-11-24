package com.leanring.nio;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/19
 */
public class BufferDemo {
    public static void main(String[] args) {
        // 构建一个byte字节缓存区，容量4
//        ByteBuffer bb = ByteBuffer.allocate(4); // 分配堆内存
        ByteBuffer bb = ByteBuffer.allocateDirect(4); // 分配堆外内存
        //默认写入模式，查看三个重要指标
        System.out.printf("初始化: capacity容量: %s, position位置: %s, limit限制: %s%n", bb.capacity(), bb.position(), bb.limit());

        // 写入3字节的数据
        bb.put((byte) 1); // 写数据
        bb.put((byte) 2);
        bb.put((byte) 3);
        // 再看数据
        System.out.printf("写入3字节后: capacity容量: %s, position位置: %s, limit限制: %s%n", bb.capacity(), bb.position(), bb.limit());

        // 转换为读模式
        System.out.println("开始读取....");
        bb.flip(); // 转换成读取模式
        byte a = bb.get(); // 获取数据
        System.out.println(a);
        byte b = bb.get();
        System.out.println(b);
        System.out.printf("读取2字节后: capacity容量: %s, position位置: %s, limit限制: %s%n", bb.capacity(), bb.position(), bb.limit());

        bb.compact();//清除已阅读的数据
        System.out.printf("清除已阅读的数据: capacity容量: %s, position位置: %s, limit限制: %s%n", bb.capacity(), bb.position(), bb.limit());
    }
}
