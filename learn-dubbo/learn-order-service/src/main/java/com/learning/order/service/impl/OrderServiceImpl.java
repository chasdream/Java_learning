package com.learning.order.service.impl;

import com.learning.order.service.OrderService;
import com.learning.rpc.config.annotation.RPCReference;
import com.learning.sms.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
@Service
public class OrderServiceImpl implements OrderService {

    @RPCReference
    private SmsService smsService;

    @Override
    public void create(String orderContent) {
        System.out.println("订单创建成功：" + orderContent);
        Object smsResult = smsService.send("10010" + UUID.randomUUID().toString().replace("-", ""), orderContent);
        System.out.println("调用smsService接口，短信发送成功：" + smsResult);
    }
}
