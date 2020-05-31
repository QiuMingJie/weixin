package com.wechat.detal.service;

import com.wechat.detal.dao.ChattingContentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author QiuMingJie
 * @date 2020-02-20 19:57
 * @description
 */
@Service
public class ChattingService {

    @Resource
    private ChattingContentRepository chattingContentRepository;


}
