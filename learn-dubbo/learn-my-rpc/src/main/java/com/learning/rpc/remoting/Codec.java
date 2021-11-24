package com.learning.rpc.remoting;

import java.util.List;

/**
 * <p>
 *  自定义处理编解码
 *  需考虑线程安全问题
 *  不同协议编解码，实现这个接口
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Codec {

    byte[] encode(Object msg) throws Exception;

    List<Object> decode(byte[] data) throws Exception;

    /**
     * 创建新的编解码实例
     *
     * @return
     */
    Codec createInstance();
}
