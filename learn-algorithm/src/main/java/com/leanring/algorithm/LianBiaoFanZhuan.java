package com.leanring.algorithm;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/29
 */
public class LianBiaoFanZhuan {

    public static void main(String[] args) {
        fanzhuan1();
        System.out.println("===================");
        fanzhuan2();
    }

    /**
     * 单链表-头插法反转链表
     */
    private static void fanzhuan2() {
        Node head = new Node(1, new Node(2, new Node(3,null)));

        System.out.println(head.toString());

        //定义一个新的空链表
        Node newNode = null;

        Node temp;
        while (head != null) {
            temp = head;
            head = temp.next;
            temp.next = newNode;
            newNode = temp;
        }

        System.out.println(newNode.toString());

    }

    /**
     * 单链表-就地逆置反转链表
     */
    private static void fanzhuan1() {
        Node head = new Node(1, new Node(2, new Node(3,null)));

        System.out.println(head.toString());

        Node begin = head;
        Node p = head.next;
        while (p != null) {

            begin.next = p.next; // 把p从链表中移除

            p.next = head; // 把p的下一个元素执行head

            head = p; // head往前移动一个位置

            p = begin.next; // 将p指向begin的下一个元素

        }

        System.out.println(head.toString());
    }
}



class Node {

    int value;

    Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
