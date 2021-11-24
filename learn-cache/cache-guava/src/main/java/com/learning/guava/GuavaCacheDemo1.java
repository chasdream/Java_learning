package com.learning.guava;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  加载方式1: CacheLoader
 *  1.设置缓存容量
 *  2.设置超时时间
 *  3.提供移除监听器
 *  4.提供缓存加载器
 *  5.构建缓存
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @date 2021-05-15
 */
public class GuavaCacheDemo1 {

    public static void main(String[] args) {
        // 提供缓存加载器
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                Thread.sleep(1000);
                if ("key".equals(key)) {
                    return null;
                }
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "'s value";
            }
        };

        // 构建移除监听器
        RemovalListener<String, String> removalListener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> removalNotification) {
                System.out.println("[" + removalNotification.getKey() + ": " + removalNotification.getValue() + "] is evicted!");
            }
        };

        // 构建LoadinngCache
        LoadingCache<String, String> testCache = CacheBuilder.newBuilder()
                .maximumSize(5) // 设置缓存容量
                .expireAfterWrite(10, TimeUnit.MINUTES) // 设置超时时间
                .removalListener(removalListener) // 提供移除监听器
                .build(loader); // 提供缓存加载器loader，构建缓存



        // 由于缓存的容量只设置5个，存入10个就会有guava基于容量回收掉3个
        for(int i = 0; i < 10; i++) {
            String key = "key" + i;
            String value = "value" + i;
            testCache.put(key, value);
            System.out.println("[" + key + ": " + value + "] is put into cache!");
        }


        // 如果存在缓存中就获取
        System.out.println(testCache.getIfPresent("key6"));

        try {
            // 不存在的key, 会报错
            System.out.println(testCache.get("key"));
        } catch (Exception e) {
            System.out.println("不存在的key，会报错");
        }
    }
}
