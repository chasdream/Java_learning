package com.learning.threadpool;

import com.learning.threadpool.domain.MyData;

/**
 * <p>
 *  线程通信方式
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/18
 */
public class ThreadCommunication implements Runnable {

    MyData myData;

    public ThreadCommunication(MyData myData) {
        this.myData = myData;
    }

    public static void main(String[] args) {
        ThreadCommunication communication = new ThreadCommunication(new MyData());
        Thread t1 = new Thread(communication);
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(communication);
        t2.setName("t2");
        t2.start();
    }

    @Override
    public void run() {
        myData.add();
    }
}
