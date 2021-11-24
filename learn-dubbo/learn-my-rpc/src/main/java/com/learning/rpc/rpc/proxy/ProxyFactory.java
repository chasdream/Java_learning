package com.learning.rpc.rpc.proxy;

import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *  代理工厂，用于生成Invoker对象
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class ProxyFactory {

    /**
     * 创建代理对象
     *
     * @param invoker
     * @param interfaces
     * @return
     */
    public static Object getProxy(Invoker invoker, Class<?>[] interfaces) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerHandler(invoker));
    }

    /**
     * 创建Invoker对象
     *
     * @param proxy
     * @param type
     * @return
     */
    public static Invoker getInvoker(Object proxy, Class<?> type) {
        return new Invoker() {
            @Override
            public Class<?> getInterface() {
                return type;
            }

            @Override
            public Object invoke(RPCInvocation rpcInvocation) throws Exception {
                // 通过反射机制获取Invoker代理对象
                Method method = proxy.getClass().getMethod(rpcInvocation.getMethodName(), rpcInvocation.getParameterTypes());
                return method.invoke(proxy, rpcInvocation.getArguments());
            }
        };
    }
}
