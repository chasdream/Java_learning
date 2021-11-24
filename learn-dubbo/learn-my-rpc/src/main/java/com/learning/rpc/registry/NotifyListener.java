package com.learning.rpc.registry;

import java.net.URI;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public interface NotifyListener {

    /**
     * 服务新增或剔除通知
     *
     * @param uris
     */
    void notify(Set<URI> uris);
}
