package com.learning.cache;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
public class CacheClient {

    public static void main(String[] args) {
        lru();
    }

    public static void lru() {
        LRU lru = new LRU();
        lru.set("k1", "123");
        lru.set("k2", "1234");
        lru.set("k3", "12345");
        lru.set("k4", "123456");
        lru.set("k5", "1234567");
//        lru.get("k1");
        System.out.println(lru.toString());
    }
}
