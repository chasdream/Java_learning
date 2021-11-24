package com.learning.threadpool;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/17
 */
public class RunnableDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableDemo1());
        thread.start();
    }

    /**
     * 创建线程的方式二：实现Runnable接口
     */
    static class RunnableDemo1 implements Runnable {
        @Override
        public void run() {
            System.out.println("创建线程池方式：实现Runnable接口");
        }
    }
}
