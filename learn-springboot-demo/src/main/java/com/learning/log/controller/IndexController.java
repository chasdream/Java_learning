package com.learning.log.controller;

import com.learning.spring.boot.autoconfigure.CustomBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/12
 */
@Controller
public class IndexController {

    @Resource
    private CustomBean customBean;

    @ResponseBody
    @RequestMapping("index")
    public String hello() {
        return "hello index demo...." + customBean.getFileName() + ": " + customBean.getFileSize();
    }

    @ResponseBody
    @RequestMapping("indexMap")
    public Map<String, Object> indexMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("12132", 1223234);
        return map;
    }
}
