package com.leanring.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/29
 */
public class Sort {

    public static void main(String[] args) {
        kuaipai();
//        maopao();
    }

    /**
     * 快速排序
     */
    private static void kuaipai() {
        int[] num = new int[]{4,5,2,3,9};
        System.out.println("排序前：" + JSON.toJSONString(num));

        int i = 0;
        int j = num.length -1;

        int temp = num[i];

        while (i < j) {


            while (temp > num[j]) {
                


            }

            j--;

        }

        System.out.println("排序后：" + JSON.toJSONString(num));
    }

    public static void maopao() {
        int[] num = new int[]{4,5,2,3,9};

        System.out.println("排序前：" + JSON.toJSONString(num));

        for (int i = 0; i<num.length; i++) {
            for (int j =i ;j<num.length; j++) {
                int temp;
                if (num[i] >num[j]) {
                    temp = num[i];
                    num[i] = num[j];
                    num[j] = temp;
                }
            }
        }
        System.out.println("排序后：" + JSON.toJSONString(num));
    }




    /**
     * 未知名排序
     */
    public static void weizhimingpaixu() {

        int[] num = new int[]{4,5,2,3,9};

        System.out.println("排序前：" + JSON.toJSONString(num));

        for (int i = 0; i < num.length; i++) {

            for (int j = num.length-1; j > i; j--) {

                int temp;
                if (num[i] > num[j]) {
                    temp = num[i];
                    num[i] = num[j];
                    num[j] = temp;
                }

            }
        }

        System.out.println("排序后：" + JSON.toJSONString(num));
    }
}
