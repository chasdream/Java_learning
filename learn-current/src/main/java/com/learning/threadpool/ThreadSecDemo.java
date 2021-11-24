package com.learning.threadpool;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  线程顺序执行
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/29
 */
public class ThreadSecDemo {


    public static void main(String[] args) {
//        abcSec();
        aOrb();
    }

    /**
     * 多个线程中任意一个线程执行完就执行一下业务
     * 成员变量标识符 方式实现
     */
    static boolean bool = false;
    public static void aOrb1()  {

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "开始执行.");
                    try {
                        int sleep = (new Random()).nextInt(1000);
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "开始结束.");
                    bool = true;
                }
            }).start();
        }

        if (bool) {
            System.out.println("主线程执行结束。。。");
        }
    }

    /**
     * 多个线程中任意一个线程执行完就执行一下业务
     * CountDownLatch 实现
     */
    public static void aOrb () {
        CountDownLatch latch1 = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    try {
                        int sleep = (new Random()).nextInt(1000);
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "开始结束");
                    latch1.countDown();
                }
            }).start();
        }

        try {
            latch1.await();
            System.out.println("主线程执行结束。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end。。。");
    }

    /**
     * a/b/c三个线程顺序执行
     * 信号量方式
     */
    public static void abcSec1() {
        Semaphore semaphore1 = new Semaphore(1);


    }

    /**
     * a/b/c三个线程顺序执行
     *
     * 方式一：CountDownLatch
     */
    public static void abcSec() {

        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    latch2.await();
                    System.out.println("线程3执行完成。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    latch3.countDown();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    latch1.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程2执行完成。。。");
//                latch2.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1执行完成。。。");
//                latch1.countDown();
            }
        }).start();
    }

}
