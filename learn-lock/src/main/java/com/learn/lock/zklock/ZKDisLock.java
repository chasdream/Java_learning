package com.learn.lock.zklock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>
 *  zookeeper分布式锁实现方式一：
 *
 *  实现原理：临时节点+watcher机制
 *
 *  步骤：
 *  1.创建临时节点加锁
 *  2.创建成功，加锁
 *  2.未创建成功，阻塞等待
 *
 *
 *  该方式会引起惊群效应，在集群规模较大的环境中带来的危害：
 *  1.巨大的服务器性能损耗
 *  2.网络冲击
 *  3.可能造成宕机
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/1
 */
public class ZKDisLock implements Lock {

    private final static String LOCKS = "zookeeper:localhost";

    private String lockPath;

    private ZkClient zkClient;

    public ZKDisLock(ZkClient zkClient, String lockPath) {
        this.zkClient = zkClient;
        this.lockPath = lockPath;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            // 阻塞等待
            waitForLock();
            // 加锁
            lock();
        }
    }

    /**
     * 阻塞等待
     */
    private void waitForLock() {
        CountDownLatch cdl = new CountDownLatch(1);
        // 增加监听器
        IZkDataListener listener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点修改成功" + dataPath);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("节点被删除了：" + dataPath);
                cdl.countDown();
            }
        };

        // 绑定监听器
        zkClient.subscribeDataChanges(lockPath, listener);

        try {
            // 如果当前节点存在，则阻塞等待
            if (zkClient.exists(lockPath)) {
                // 阻塞等待线程
                cdl.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 取消监听器注册
        zkClient.unsubscribeDataChanges(lockPath, listener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(lockPath, LOCKS);
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        zkClient.delete(lockPath);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
