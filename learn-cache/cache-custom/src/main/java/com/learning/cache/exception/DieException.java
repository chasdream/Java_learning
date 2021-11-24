package com.learning.cache.exception;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
public class DieException extends RuntimeException {
    public DieException() {
    }

    public DieException(String message) {
        super(message);
    }

    public DieException(String message, Throwable cause) {
        super(message, cause);
    }

    public DieException(Throwable cause) {
        super(cause);
    }

    public DieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
