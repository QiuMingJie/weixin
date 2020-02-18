package com.wechat.detal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiuMingJie
 * @date 2020-02-18 12:11
 * @description
 */
@RestController
@RequestMapping("/form")
public class AccectController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String aa() {
        return "222";
    }
}
