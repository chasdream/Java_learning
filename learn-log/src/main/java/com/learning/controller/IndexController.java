package com.learning.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/10
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/log")
    public String log() {
//        MDC.put("userId", "1230192-1");
        logger.info("log....");
        logger.debug("log....");
        return "success";
    }
}
