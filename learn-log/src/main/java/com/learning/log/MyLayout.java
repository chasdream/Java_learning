package com.learning.log;

import ch.qos.logback.classic.PatternLayout;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
public class MyLayout extends PatternLayout {

    static {
        defaultConverterMap.put("userId", MyUserIdConverter.class.getName());
        defaultConverterMap.put("userName", MyUserNameConverter.class.getName());
    }

}
