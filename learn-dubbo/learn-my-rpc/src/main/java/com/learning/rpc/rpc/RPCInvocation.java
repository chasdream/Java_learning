package com.learning.rpc.rpc;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 *  客户端请求
 *  保留一次调用相关的目的地、参数、每次都有唯一的ID
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCInvocation implements Serializable {

    private static final long serialVersionUID = 5775625767260840409L;

    static AtomicLong SEQ = new AtomicLong();

    private long id;

    private String serviceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    public RPCInvocation() {
        // 初始化一个ID
        this.setId(incrementAndGet());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "RPCInvocation{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    public final long incrementAndGet() {
        long current;
        long next;
        do {
            current = SEQ.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!SEQ.compareAndSet(current, next));
        return next;
    }
}
