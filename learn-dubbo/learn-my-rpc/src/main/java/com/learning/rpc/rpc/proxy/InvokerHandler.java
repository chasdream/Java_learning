package com.learning.rpc.rpc.proxy;

import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 *  jdk 动态代理
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class InvokerHandler implements InvocationHandler {

    private final Invoker invoker;

    public InvokerHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }

        // 获取方法名称
        String methodName = method.getName();
        // 获取方法参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            if ("toString".equals(methodName)) {
                return invoker.toString();
            } else if ("$destroy".equals(methodName)) {
                return null;
            } else if ("hashCode".equals(methodName)) {
                return invoker.hashCode();
            }
        } else if (parameterTypes.length == 1 && "equals".equals(methodName)) {
            return invoker.equals(args[0]);
        }

        // 将真实的Invoker对象代理成RPCInvocation对象
        RPCInvocation rpcInvocation = new RPCInvocation();
        rpcInvocation.setMethodName(methodName);
        rpcInvocation.setArguments(args);
        rpcInvocation.setParameterTypes(parameterTypes);
        rpcInvocation.setServiceName(method.getDeclaringClass().getName());

        return invoker.invoke(rpcInvocation);
    }
}
