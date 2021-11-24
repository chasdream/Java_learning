package com.learning.rpc.remoting;

import java.net.URI;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface Server {

    void start(URI uri, Codec codec, Handler handler);
}
