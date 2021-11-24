package com.learning.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping("index")
    public String hello() {
        return "hello index....";
    }

    @ResponseBody
    @RequestMapping("indexMap")
    public Map<String, Object> indexMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("12132", 122323);
        return map;
    }
}
