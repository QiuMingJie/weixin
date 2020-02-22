package com.wechat.detal.common.util;

import com.wechat.detal.config.Config;
import com.wechat.detal.config.ReturnDict;
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
        messageStreamDto = createMsg(Config.adminWechatId1, msg);
        if (CommonUtils.notEmpty(messageStreamDto)) {
            MessageStreamDto.resultMessageStreamDto.add(messageStreamDto);
        }
        messageStreamDto = createMsg(Config.adminWechatId2, msg);
        if (CommonUtils.notEmpty(messageStreamDto)) {
            MessageStreamDto.resultMessageStreamDto.add(messageStreamDto);
        }
    }

    /**
     * 发送信息，可用于群发
     *
     * @param ids
     * @return
     */
    public static void sendMsg(List<String> ids, String msg) {
        if (CommonUtils.notEmpty(ids) && CommonUtils.notEmpty(msg)) {
            ids.forEach(x->MessageStreamDto.resultMessageStreamDto.add(createMsg(x,msg)));
        }
    }

    public static void sendMsg(String id, String msg) {
        if (CommonUtils.notEmpty(id) && CommonUtils.notEmpty(msg)) {
            MessageStreamDto.resultMessageStreamDto.add(createMsg(id,msg));
        }
    }

    /**
     * 发送信息
     *
     * @param sendToId
     * @param msg
     * @param photoDesHtml  图片，描述和网址，其中图片假如是网址可以直接，假如是文件file:///C:/Users/Qiu/Desktop/12.jpg
     *
     * @return
     */
    public static void sendContent(String returnType,String sendToId, String msg,String... photoDesHtml) {
        if (CommonUtils.notEmpty(sendToId) && CommonUtils.notEmpty(msg)) {
            if (CommonUtils.empty(returnType)) {
                MessageStreamDto.resultMessageStreamDto.add(new MessageStreamDto(ReturnDict.SEND_MSG, sendToId, msg));
            } else if (ReturnDict.SEND_XML.equals(returnType)) {
                if (CommonUtils.notEmpty(photoDesHtml) && photoDesHtml.length == 3) {
                    MessageStreamDto.resultMessageStreamDto.add(new MessageStreamDto(returnType, sendToId, msg,photoDesHtml[0],photoDesHtml[1],photoDesHtml[2]));
                }
            }else {
                MessageStreamDto.resultMessageStreamDto.add(new MessageStreamDto(returnType, sendToId, msg));
            }
        }
    }

    private static MessageStreamDto createMsg(String id, String msg, String type) {
        if (CommonUtils.notEmpty(id) && CommonUtils.notEmpty(msg)) {
            if (CommonUtils.empty(type)) {
                return new MessageStreamDto(ReturnDict.SEND_MSG, id, msg);
            } else {
                return new MessageStreamDto(type, id, msg);
            }
        }
        return null;
    }

    private static MessageStreamDto createMsg(String id, String msg) {
        return createMsg(id, msg, "");
    }


}
