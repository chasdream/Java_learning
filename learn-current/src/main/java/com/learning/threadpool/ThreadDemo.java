package com.learning.threadpool;

import java.util.Arrays;

/**
 * <p>
 *  创建线程的三种方式
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/17
 */
public class ThreadDemo {

    public static void main(String[] args) {

        ThreadDemo1 demo1 = new ThreadDemo1();
        // 启动线程的唯一方法，该方法将启动一个新的线程，并执行run()方法
        // start()调用的是一个native方法
        demo1.start();

        System.out.println("主线程执行");
    }

    /**
     * 方式一：继承Thread
     * 本质是实现了一个Runnable接口的一个实例，代表一个线程实例
     */
    static class ThreadDemo1 extends Thread {

        @Override
        public void run() {
            try {
                // 睡眠3秒
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("创建线程池方式：继承Thread");
        }
    }
}
