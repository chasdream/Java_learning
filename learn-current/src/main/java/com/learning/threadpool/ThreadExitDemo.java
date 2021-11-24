package com.learning.threadpool;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/18
 */
public class ThreadExitDemo extends Thread {

    // 线程执行结束标志位
    private volatile boolean exit = false;

    @Override
    public void run() {
        int i = 0;
        while (!exit) {
            if (i == 6) {
                exit = true;
            } else {
                i++;
            }
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        ThreadExitDemo demo = new ThreadExitDemo();
        demo.start();
    }
}
