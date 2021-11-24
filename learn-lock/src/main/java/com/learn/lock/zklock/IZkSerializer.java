package com.learn.lock.zklock;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * <p>
 *  zk节点序列化
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/1
 */
public class IZkSerializer implements ZkSerializer {
    /**
     * 序列化
     * @param data
     * @return
     * @throws ZkMarshallingError
     */
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        return new byte[0];
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     * @throws ZkMarshallingError
     */
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return null;
    }
}
