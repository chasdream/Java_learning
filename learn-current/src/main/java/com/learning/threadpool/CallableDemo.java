package com.learning.threadpool;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/17
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new CallableDemo1());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }

    /**
     * 创建线程方式三：实现Callable接口，可以返回任务的结果
     */
    static class CallableDemo1 implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "创建线程池方式：实现Callable接口, 并返回结果";
        }
    }
}
