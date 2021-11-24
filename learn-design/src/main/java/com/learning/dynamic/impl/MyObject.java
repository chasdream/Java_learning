package com.learning.dynamic.impl;

import com.learning.dynamic.MyObjectImpl;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class MyObject implements MyObjectImpl {
    @Override
    public void say() {
        System.out.println("say myObjectImpl......");
    }
}
