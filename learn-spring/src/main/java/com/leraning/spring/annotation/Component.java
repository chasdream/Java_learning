package com.leraning.spring.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *  bean扫描组件注解
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
