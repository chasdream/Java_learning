package com.learning.rpc.rpc;

/**
 * <p>
 *  1.消费者调用服务，通过Invoker对象
 *  2.服务提供者调用具体实现类，通过Invoker对象
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Invoker {

    /**
     * 返回接口
     *
     * @return
     */
    Class<?> getInterface();

    /**
     * 发起调用【负载均衡、容错、重连 等功能】
     * @param rpcInvocation 调用所需要的参数
     * @return 执行结果
     * @throws Exception
     */
    Object invoke(RPCInvocation rpcInvocation) throws Exception;
}
