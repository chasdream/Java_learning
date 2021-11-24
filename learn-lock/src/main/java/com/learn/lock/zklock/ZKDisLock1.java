package com.learn.lock.zklock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>
 *
 * zookeeper分布式锁实现方式一：
 *
 * 实现原理：临时顺序节点+watcher+取最小号
 *
 * 问题：当所有线程都共享一个会话，节点顺序不能确定
 * 解决方案：让所有线程都使用自己的会话
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/1
 */
public class ZKDisLock1 implements Lock {

    private String lockPath;

    private ZkClient  zkClient;

    /**
     * 当前节点
     */
    private String currentPath;

    /**
     * 上一个节点
     */
    private String beforePath;

    public ZKDisLock1(String lockPath) {
        this.lockPath = lockPath;
        zkClient = new ZkClient("");
        zkClient.setZkSerializer(new IZkSerializer());
        // 判断节点释放存在，不存在就创建一个节点
        if (!this.zkClient.exists(lockPath)) {
            try {
                // 创建一个持久节点
                this.zkClient.createPersistent(lockPath);
            } catch (Exception e) {
                // 异常不做处理
                e.printStackTrace();
            }
        }
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            //阻塞等待
            waitForLock();
            lock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        // 判断当前节点释放存在
        if (this.currentPath == null) {
            currentPath = this.zkClient.createEphemeralSequential(lockPath + "/", "localhost");
        }

        // 获取所有的临时子节点
        List<String> childrenList = this.zkClient.getChildren(lockPath);

        // 对临时子节点进行排序
        Collections.sort(childrenList);

        // 判断当前节点是否是最小的，如果当前节点等于子节点的第一个值，则返回true，否则
        if (currentPath.equals(lockPath + "/" + childrenList.get(0))) {
            return true;
        } else {
            // 取子节点中第一个临时顺序节点
            // 获取第一个节点的索引
            int index = childrenList.indexOf(currentPath.substring(lockPath.length() + 1));
            // 获取前一个临时节点路径
            beforePath = lockPath + "/" + childrenList.get(index - 1);

        }

        return false;
    }

    private void waitForLock () {

        // 定义CountdownLatch对锁线程进行阻塞
        CountDownLatch latch = new CountDownLatch(1);

        // 定义Zk listener
        IZkDataListener listener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点修改成功" + dataPath);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("节点被删除了：" + dataPath);
            }
        };

        // 订阅监听前一个节点
        zkClient.subscribeDataChanges(beforePath, listener);

        // 阻塞线程
        try {
            if (zkClient.exists(beforePath)) {
                latch.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 取消监听器注册
        zkClient.unsubscribeDataChanges(beforePath, listener);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        this.zkClient.delete(beforePath);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
