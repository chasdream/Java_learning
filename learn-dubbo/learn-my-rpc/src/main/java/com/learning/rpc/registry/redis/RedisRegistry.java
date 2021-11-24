package com.learning.rpc.registry.redis;

import com.learning.rpc.common.tools.URIUtils;
import com.learning.rpc.registry.NotifyListener;
import com.learning.rpc.registry.RegistryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * redis 实现注册中心
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class RedisRegistry implements RegistryService {

    // 30秒过期时间
    private static final int TIME_OUT = 30;

    URI address;

    // 心跳检测任务
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    // 服务提供者：心跳列表，在列表的服务标识存活
    ArrayList<URI> serviceHeartBeat = new ArrayList<>();

    // 服务消费者相关
    JedisPubSub jedisPubSub;

    Map<String, Set<URI>> localCache = new ConcurrentHashMap<>();

    Map<String, NotifyListener> listenerMap = new ConcurrentHashMap<>();

    /**
     * 服务注册
     *
     * @param uri RPCProtocol://127.0.0.1:8088/com.learning.sms.service.SmsService?transporter=Netty4Transporter&serialization=JSONSerialization
     */
    @Override
    public void register(URI uri) {
        // key = rpc-[uri.toString]
        String key = "rpc-" + uri.toString();
        Jedis jedis = new Jedis(address.getHost(), address.getPort());
        jedis.setex(key, TIME_OUT, String.valueOf(System.currentTimeMillis()));
        jedis.close();
        // 开始心跳检测，服务是否存活
        serviceHeartBeat.add(uri);
    }

    /**
     * 服务订阅
     *
     * @param service        注册的服务名
     * @param notifyListener 监听接口
     */
    @Override
    public void subscribe(String service, NotifyListener notifyListener) {
        try {

            if (localCache.get(service) == null) {
                localCache.putIfAbsent(service, new HashSet<>());
                listenerMap.putIfAbsent(service, notifyListener);
                // 第一次直接在redis中获取
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                String key = "rpc-" + service;
                Set<String> serviceInstances = jedis.keys("rpc-*" + service + "?*");
                for (String instances : serviceInstances) {
                    URI instanceUri = new URI(instances.replace("rpc-", ""));
                    localCache.get(service).add(instanceUri);
                }
                notifyListener.notify(localCache.get(service));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(URI address) {

        this.address = address;

        // 启动定时任务检测服务提供者心跳是否正常
        executor.scheduleWithFixedDelay(() -> {

            // 心跳，延长服务注册时间
            Jedis jedis = new Jedis(address.getHost(), address.getPort());

            try {
                for (URI uri : serviceHeartBeat) {
                    String key = "rpc-" + uri.toString();
                    jedis.setex(key, TIME_OUT, String.valueOf(System.currentTimeMillis()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jedis.close();
            }
        }, 3000, 5000, TimeUnit.MILLISECONDS);

        // 动态监听redis上key的变动，更新本地缓存中的service列表, 在redis.conf配置文件开启通知机制
        executor.execute(new Runnable() {
            @Override
            public void run() {
                jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                        System.out.println("注册中心开始监听：" + pattern);
                    }

                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                        try {

                            URI serviceURI = new URI(channel.replace("__keyspace@0__:rpc-", ""));
                            if ("set".equals(message)) {
                                // 新增
                                Set<URI> uris = localCache.get(URIUtils.getService(serviceURI));
                                if (uris != null) {
                                    uris.add(serviceURI);
                                }
                            }
                            if ("expired".equals(message)) {
                                // 过期
                                Set<URI> uris = localCache.get(URIUtils.getService(serviceURI));
                                if (uris != null) {
                                    uris.remove(serviceURI);
                                }
                            }
                            if ("set".equals(message) || "expired".equals(message)) {
                                System.out.println("服务实例有变化，开始刷新");
                                NotifyListener notifyListener = listenerMap.get(URIUtils.getService(serviceURI));
                                if (notifyListener != null) {
                                    notifyListener.notify(localCache.get(URIUtils.getService(serviceURI)));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                jedis.psubscribe(jedisPubSub, "__keyspace@0__:rpc-*");
            }
        });
    }
}
