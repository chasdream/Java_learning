package com.learning.sms.service;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public interface SmsService {

    Object send(String phone, String message);
}
