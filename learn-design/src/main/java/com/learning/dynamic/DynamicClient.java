package com.learning.dynamic;

import com.learning.dynamic.impl.MyObject;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/8
 */
public class DynamicClient {

    public static void main(String[] args) throws Exception {


        MyObject obj = new MyObject();

        MyObjectImpl object = (MyObjectImpl) Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new MyProxyHandler(obj)
        );

        object.say();

//        Class clazz = Class.forName("com.learning.dynamic.impl.MyObject");
//        Object obj = clazz.newInstance();
//
//        Method method = clazz.getMethod("say");
//        Object object = method.invoke(obj);
//        System.out.println(object);
    }
}
