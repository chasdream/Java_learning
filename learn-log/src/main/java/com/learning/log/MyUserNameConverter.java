package com.learning.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
public class MyUserNameConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        return "zhangsan";
    }
}
