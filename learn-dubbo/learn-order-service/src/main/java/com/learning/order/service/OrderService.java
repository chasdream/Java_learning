package com.learning.order.service;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderContent 订单内容
     */
    void create(String orderContent);
}
