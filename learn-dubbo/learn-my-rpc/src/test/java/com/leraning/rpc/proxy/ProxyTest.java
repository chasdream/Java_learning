package com.leraning.rpc.proxy;

import com.learning.rpc.rpc.Invoker;
import com.learning.rpc.rpc.RPCInvocation;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class ProxyTest {

    public static void main(String[] args) {

        Invoker invoker = new Invoker() {
            @Override
            public Class<?> getInterface() {
                return null;
            }

            @Override
            public Object invoke(RPCInvocation rpcInvocation) throws Exception {
                return null;
            }
        };

    }
}
