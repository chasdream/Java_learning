package com.learning.cachemap;

import org.junit.Test;

import java.util.UUID;

/**
 * <p>
 *   缓存测试类
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @date 2021-05-14
 */
public class CacheTest {

    @Test
    public void cacheMapTest() throws InterruptedException {
        CacheProvider cacheProvider = new CacheProvider();

        String key = "id";

        // 不设置过期时间
        System.out.println("******************不设置过期时间******************");
        cacheProvider.put(key, 123);
        System.out.println("   get key:" + key + ", value:" + cacheProvider.get(key));
        System.out.println("remove key:" + key + ", value:" + cacheProvider.remove(key));
        System.out.println("   get key:" + key + ", value:" + cacheProvider.get(key));

        // 设置过期时间
        System.out.println("******************设置过期时间******************");
        cacheProvider.put(key, 123456, 1000L);
        System.out.println("   get key:" + key + ", value:" + cacheProvider.get(key));

        Thread.sleep(2000);
        System.out.println("   get key:" + key + ", value:" + cacheProvider.get(key));
    }

    @Test
    public void fifoCacheMapTest() {
        FIFOCacheProvider provider = new FIFOCacheProvider(10);
        for (int i = 0; i < 15; i++) {
            provider.put("id" +i, UUID.randomUUID());
        }
        System.out.println("缓存的大小：" + provider.size());
        System.out.println("缓存的数据：\n" + provider.toString());
    }
}
