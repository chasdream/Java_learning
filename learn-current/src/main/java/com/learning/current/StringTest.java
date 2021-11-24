package com.learning.current;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/5/17
 */
public class StringTest {

    public static void main(String[] args) {


        String str1 = "123";

        String str2 = "123";

        String str3 = new String("123");

        String str4 = str1;

        String str5 = str3;

        String str6 = new String("123");

        System.out.println("str1.equals(str2) : " + (str1.equals(str2)));
        System.out.println("str1 == str2 : " + (str1 == str2));

        System.out.println("str1.equals(str3) : " + (str1.equals(str3)));
        System.out.println("str1 == str3 : " + (str1 == str3));

//        System.out.println("str2 == str4 : " + (str2.equals(str4)));
//        System.out.println("str1 == str5 : " + (str1.equals(str5)));
        System.out.println("str3.equals(str6) : " + (str3.equals(str6)));
        System.out.println("str3 == str6 : " + (str3 == str6));
    }
}
