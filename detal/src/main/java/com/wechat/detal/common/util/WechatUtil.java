package com.wechat.detal.common.util;

import com.wechat.detal.config.Config;
import com.wechat.detal.dto.MessageStreamDto;
import com.wechat.detal.service.ChattingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author QiuMingJie
 * @date 2020-02-20 17:52
 * @description 微信传出的工具
 */
public class WechatUtil {
    @Autowired
    private static ChattingService chattingService;


    private static MessageStreamDto messageStreamDto;

    /**
     * 发送给管理员信息
     *
     * @param msg
     * @return
     */
    public static void sendAdminMsg(String msg) {
        MessageStreamDto.resultMessageSreamDto.add(createMsg(Config.adminWechatId1, msg));
        MessageStreamDto.resultMessageSreamDto.add(createMsg(Config.adminWechatId1, msg));
    }

    /**
     * 发送信息，可用于群发
     *
     * @param ids
     * @return
     */
    public static void createMsgs(List<String> ids, String msg) {
        if (CommonUtils.notEmpty(ids) && CommonUtils.notEmpty(msg)) {
            ids.forEach(x->MessageStreamDto.resultMessageSreamDto.add(createMsg(x,msg)));
        }
    }

    /**
     * 发送信息
     *
     * @param id
     * @param msg
     * @return
     */
    public static void sendMsg(String id, String msg, String... type) {
        if (CommonUtils.notEmpty(id) && CommonUtils.notEmpty(msg)) {
            if (CommonUtils.empty(type)) {
                MessageStreamDto.resultMessageSreamDto.add(new MessageStreamDto("10100", id, msg));
            } else {
                MessageStreamDto.resultMessageSreamDto.add(new MessageStreamDto(type[0], id, msg));
            }
        }
    }

    private static MessageStreamDto createMsg(String id, String msg, String... type) {
        if (CommonUtils.notEmpty(id) && CommonUtils.notEmpty(msg)) {
            if (CommonUtils.empty(type)) {
                return new MessageStreamDto("10100", id, msg);
            } else {
                return new MessageStreamDto(type[0], id, msg);
            }
        }
        return null;
    }


}
