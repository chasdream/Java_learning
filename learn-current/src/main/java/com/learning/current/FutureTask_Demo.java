package com.learning.current;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/8
 */
public class FutureTask_Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MINUTES, new LinkedBlockingDeque<>());

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "lllll";
            }
        };

        Future<String> future = executor.submit(callable);
        System.out.println("args = " + future.get());


        //// FutureTask
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println("futureTask = " + futureTask.get());

    }
}
