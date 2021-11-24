package com.learning.current;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021-05-17
 */
public class VisibilityDemo {

    public volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        VisibilityDemo visibilityDemo = new VisibilityDemo();

        System.out.println("代码要开始运行了......");

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while (visibilityDemo.flag) {
                    i++;
                }

                System.out.println("i = " + i);
            }
        }).start();

        Thread.sleep(2000);

        visibilityDemo.flag = false;

        System.out.println("flag 被置为false了");
    }
}
