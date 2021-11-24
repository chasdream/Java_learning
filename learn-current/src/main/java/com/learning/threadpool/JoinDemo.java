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
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "线程开始运行！");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程开始执行！");
            }
        });
        t1.setName("线程B");
        t1.start();
        t1.join();
        System.out.println(t1.getName() + "执行完毕后才能执行主线程！");
    }
}
