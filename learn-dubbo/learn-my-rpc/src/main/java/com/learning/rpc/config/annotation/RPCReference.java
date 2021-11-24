package com.learning.rpc.config.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RPCReference {

    /**
     * 选择负载均衡方式
     *
     * @return
     */
    String loadBalance() default "RandomLoadBalance";
}
