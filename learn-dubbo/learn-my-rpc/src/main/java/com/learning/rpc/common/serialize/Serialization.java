package com.learning.rpc.common.serialize;

/**
 * <p>
 *  序列化和反序列化
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Serialization {

    /**
     * 序列化
     *
     * @param output
     * @return
     * @throws Exception
     */
    byte[] serialize(Object output) throws Exception;

    /**
     * 反序列化
     *
     * @param input
     * @param clazz
     * @return
     * @throws Exception
     */
    Object deserialize(byte[] input, Class<?> clazz) throws Exception;
}
