package com.learning.rpc.config.spring.annotation;

import com.learning.rpc.config.spring.RPCConfiguration;
import com.learning.rpc.config.spring.RPCPostProcessor;
import org.springframework.context.annotation.Import;

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
@Import({RPCPostProcessor.class, RPCConfiguration.class}) // spring启动是自动执行该段代码
public @interface EnableRPC {
}
