package com.learning.rpc.config.annotation;

import org.springframework.stereotype.Service;

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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface RPCService {

    Class<?> interfaceClass() default void.class;
}
