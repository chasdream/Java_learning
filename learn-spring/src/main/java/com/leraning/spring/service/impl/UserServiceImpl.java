package com.leraning.spring.service.impl;

import com.leraning.spring.annotation.Component;
import com.leraning.spring.service.UserService;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/14
 */
@Component
public class UserServiceImpl implements UserService {


    @Override
    public String getUser() {
        return "xiaoming";
    }
}
