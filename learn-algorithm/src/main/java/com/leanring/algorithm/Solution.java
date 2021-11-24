package com.leanring.algorithm;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/7/23
 */
public class Solution {

    public static void main(String[] args) {
//        int[][] arr = {{1,2}, {3,4}, {5,6}};
        int[][] arr = {{1,50}};
        int left = 1;
        int right = 50;
        boolean bool = true;
        for (int i = left; i <= right; i++) {
            if (array(arr, i)) {
                continue;
            }
            bool = false;
            break;
        }
        System.out.println(bool);
    }

    public static boolean array(int[][] arr, int index) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == index) {
                    return true;
                }
            }
        }
        return false;
    }
}
