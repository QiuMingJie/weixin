package com.wechat.detal.service;

import dao.ConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author QiuMingJie
 * @date 2020-02-18 12:20
 * @description
 */
@Service
public class ConfigService {
    @Resource
    private ConfigRepository configRepository;

    public String get() {
        return "";
    }
}
