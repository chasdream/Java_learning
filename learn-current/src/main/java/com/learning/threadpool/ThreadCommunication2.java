package com.learning.threadpool;

/**
 * <p>
 *  线程通信方式
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/18
 */
public class ThreadCommunication2 {

    private volatile int i = 0;



    public static void main(String[] args) {

    }

    class RunDemo implements Runnable {

        @Override
        public void run() {
            i++;
        }
    }

}
