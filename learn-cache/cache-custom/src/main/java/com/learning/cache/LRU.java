package com.learning.cache;

import com.alibaba.fastjson.JSON;
import com.learning.cache.exception.DieException;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *  LRU内存淘汰策略
 *  最近最少使用：最近一段时间未被访问的数据，在未来被访问的概率较低
 *
 *  淘汰策略：淘汰最近一段时间未被访问的数据
 *
 *  实现方案：
 *  定义一个双向链表用于数据存储，新数据放入链表表头，淘汰尾节点数据
 *      1.设置缓存数据，如果不存在链表节点中，直接放入头节点
 *      2.如果缓存数据链表中存在，则将节点数据放入到链表头节点
 *      3.链表长度达到最大值，淘汰链表尾节点数据
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
public class LRU {
    /**
     * 链表最大长度
     */
    private static final int THRESHOLD = 4;

    /**
     * 链表实际大小
     */
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * 定义头节点
     */
    private Node head;

    /**
     * 设置缓存数据
     *
     * @param key 缓存数据key
     * @param data 缓存数据
     */
    public void set(String key, Object data) {
        if (key == null || key.equals("")) {
            throw new DieException("key is null");
        }
        // head为空，创建头节点
        if (head == null) {
            head = new Node(key, data, null, null);
        } else {
            // head不为空，在追加头节点
            // key存在，将key对应的节点数据移动到头节点（如果data不同，覆盖），删除节点中历史的数据
            remove(key);

            // size大于THRESHOLD，删除尾节点数据
            if (size() >= THRESHOLD) {
                removeLast();
            }

            // 头节点追加新节点数据
            head.pre = new Node(key, data, null, head);
            head = head.pre;
        }
        // 链表长度加1
        count.incrementAndGet();
    }

    /**
     * 查询缓存数据
     *
     * 查询缓存是说明缓存被使用了，再次访问的概率提升，将缓存放到头节点
     *
     * @param key 缓存数据key
     * @return
     */
    public Object get(String key) {
        Node tail = head;
        while (tail != null) {
            // 如果key相同，先从列表中删除，再插入链表表头
            if (tail.key.equals(key)) {

                // 头节点，则不需要删除操作，直接返回结果
                if (tail.pre == null) {
                    return tail.data;
                }

                // 不是头节点，将节点删除
                tail.pre.next = tail.next;

                // 不是尾节点的情况
                if (tail.next != null) {
                    tail.next.pre = tail.pre;
                } else {
                    // 尾节点的前一个节点置为空
                    tail.pre = null;
                }

                // 将节点插入到头节点
                tail.next = head;
                head.pre = tail;
                head = head.pre;
                return tail.data;
            }
            tail = tail.next;
        }
        return null;
    }

    /**
     * 删除缓存数据
     *
     * @param key 缓存数据key
     */
    public void remove(String key) {
        Node tail = head;
        while (tail != null) {
            // 如果key相同则移除该节点
            if (tail.key.equals(key)) {
                tail.pre.next = tail.next;
                tail.next.pre = tail.pre;
                // 链表长度减1
                count.decrementAndGet();
                return;
            }
            tail = tail.next;
        }
    }

    /**
     * 删除链表中最后一个节点数据
     */
    public void removeLast() {
        Node tail = head;
        while (tail != null) {
            // 如果tail.next=null，则表示链表是最好一个，直接移除
            if (tail.next == null) {
                tail.pre.next = null;
                // 链表长度减1
                count.decrementAndGet();
            }
            tail = tail.next;
        }
    }

    /**
     * 获取链表长度
     *
     * @return
     */
    public int size() {
        return count.get();
    }

    /**
     * 判断缓存数据key是否存在
     *
     * @param key 缓存数据key
     * @return
     */
    public boolean exists(String key) {
        Node tail = head;
        while (tail != null) {
            if (tail.key.equals(key)) {
                return true;
            }
            tail = tail.next;
        }
        return false;
    }

    /**
     * 双向链表反转
     */
    public void reverse() {
        if (head == null) {
            throw new DieException("head is null");
        }
        Node tail = head;
        while (tail != null) {
            Node temp = tail.next;
            tail.next = tail.pre;
            tail.pre = temp;
            tail = tail.next;
        }
    }

    /**
     * 定义链表节点数据
     */
    @Data
    static class Node {
        // 缓存key
        private String key;
        // 缓存数据
        private Object data;
        // 前一个节点
        private Node pre;
        // 后一个节点
        private Node next;

        public Node(String key, Object data, Node pre, Node next) {
            this.data = data;
            this.key = key;
            this.pre = pre;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        return "LRU{" +
                "count=" + count.get() +
                ", head=" + JSON.toJSONString(head) +
                '}';
    }
}
