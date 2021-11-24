package com.learning.sms.service.impl;

import com.learning.rpc.config.annotation.RPCService;
import com.learning.sms.service.SmsService;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
@RPCService
public class SmsServiceImpl implements SmsService {

    @Override
    public Object send(String phone, String message) {
        return "手机号:" + phone + "短信发送成功：" + message;
    }
}
