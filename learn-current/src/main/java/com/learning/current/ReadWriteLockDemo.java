package com.learning.current;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/4
 */
public class ReadWriteLockDemo {

    volatile long i = 0;

    Lock lock = new ReentrantLock();

    // 读写锁
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void read() {
//        lock.lock();
        rwLock.readLock().lock();
        long a = i;
//        lock.unlock();
        rwLock.readLock().unlock();
    }

    public void write() {
//        lock.lock();
        rwLock.writeLock().lock();
        i++;
//        lock.unlock();
        rwLock.writeLock().unlock();
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i <= 30; i++) {
            int n = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (n%3 == 0) {
                        demo.write();
                    } else {
                        demo.read();
                    }
                }
            }).start();
        }

    }
}
