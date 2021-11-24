package com.learning.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class MyProxyHandler implements InvocationHandler {

    private Object object;

    public MyProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before....");
        Object obj = method.invoke(object, args);
        System.out.println("after....");
        return obj;
    }
}
