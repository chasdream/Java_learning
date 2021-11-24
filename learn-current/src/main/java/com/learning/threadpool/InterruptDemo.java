package com.learning.threadpool;

/**
 * <p>
 *  线程中断方法interrupt()
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/18
 */
public class InterruptDemo extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) { // 非阻塞状态通过判断中断标志来退出线程
            try {
                Thread.sleep(5000);// 阻塞状态通过异常捕获，break跳出循环来中断线程
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
