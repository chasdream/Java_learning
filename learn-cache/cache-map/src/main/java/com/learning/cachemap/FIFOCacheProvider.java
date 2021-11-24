package com.learning.cachemap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     基于JVM map的内存缓存工具类，FIFO先进先出策略淘汰键，避免占用内存过大
 * </p>
 *
 * @author harber
 * @date   2021-05-14
 * @version 1.0.0
 */
public class FIFOCacheProvider {

    // 定义全局缓存集合
    private Map<String, CacheData> cacheDataMap = null;

    // 定时器线程池，用于清除过期的缓存
    private final static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    // FIFO 设置最大缓存容量
    private static int MAX_CACHE_SIZE = 0;

    // 填充因子
    private final static float LOAD_FACTORY = 0.75F;

    // 构造函数，需要设置缓存的大小
    public FIFOCacheProvider(int maxCacheSize) {
        MAX_CACHE_SIZE = maxCacheSize;
        // 根据 cacheSize 和 LOAD_FACTORY 计算cache容量
        int capacity = (int) (Math.ceil(MAX_CACHE_SIZE/LOAD_FACTORY) + 1);
        cacheDataMap = new LinkedHashMap<String, CacheData>(capacity, LOAD_FACTORY, false){
            // 重写LinkedHashMap的remove方法
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheData> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }


    /**
     * 读取缓存中数据
     *
     * @param key 存储缓存的key
     * @param <T> 返回对象泛型
     * @return 返回对应的对象信息
     */
    public synchronized <T> T get(String key) {
        CacheData cacheData = cacheDataMap.get(key);
        return null == cacheData ? null : (T) cacheData.data;
    }

    /**
     * 添加缓存内容，-1表示缓存永不过期，默认不失效
     *
     * @param key 存储缓存的key
     * @param value 缓存的对象
     */
    public synchronized void put(String key, Object value) {
        this.put(key, value, -1L);
    }

    /**
     * 添加缓存内容，需要设置过期时间
     *
     * @param key 存储缓存的key
     * @param value 缓存的对象
     * @param expire 过期时间，单位：毫秒，-1表示不过期
     */
    public synchronized void put(String key, Object value, Long expire) {

        // 写入缓存之前，清除原数据
        cacheDataMap.remove(key);

        // 大于0才设置过期时间，否则没有意义
        if (expire > 0) {

            // 定时器线程池，用于清除过期的缓存
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    // 过期后清除缓存
                    synchronized (this) {
                        cacheDataMap.remove(key);
                    }
                }
            }, expire, TimeUnit.MILLISECONDS);

            cacheDataMap.put(key, new CacheData(value, expire));
        } else {
            // 不设置过期时间
            cacheDataMap.put(key, new CacheData(value, -1L));
        }
    }

    /**
     * 删除缓存
     *
     * @param key 存储缓存的key
     * @param <T> 返回对象泛型
     * @return 返回对应的对象信息
     */
    public synchronized <T> T remove(String key) {
        // 清除原数据
        CacheData cacheData = cacheDataMap.remove(key);
        return null == cacheData ? null : (T) cacheData.data;
    }

    /**
     * 查询当前缓存的数量
     *
     * @return 返回缓存键值对的个数
     */
    public synchronized int size() {
        return cacheDataMap.size();
    }


    /**
     * 缓存实体类
     */
    private static class CacheData {

        // 缓存数据
        public Object data;

        // 失效时间
        public Long expire;

        public CacheData() {
        }

        public CacheData(Object data, Long expire) {
            this.data = data;
            this.expire = expire;
        }
    }

    /**
     * 重写toString方法
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CacheData> entry : cacheDataMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue().data).append("\n");
        }
        return sb.toString();
    }
}
