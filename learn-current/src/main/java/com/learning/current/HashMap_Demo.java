package com.learning.current;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 *  读写锁-缓存
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/4
 */
public class HashMap_Demo {

    private final static Map<String, Object> cache = new HashMap<>();

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock readLock = rwLock.readLock();

    private final Lock writeLock = rwLock.writeLock();

    public Object get(String key) {
        readLock.lock();
        try {
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Object put(String key, Object object) {
        writeLock.lock();
        try {
            return cache.put(key, object);
        } finally {
            writeLock.unlock();
        }
    }

    public Object[] allKeys() {
        readLock.lock();
        try {
            return cache.keySet().toArray();
        } finally {
            readLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            cache.clear();
        } finally {
            readLock.unlock();
        }
    }
}




















