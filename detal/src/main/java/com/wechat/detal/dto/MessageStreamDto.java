package com.wechat.detal.dto;

import com.alibaba.fastjson.JSONObject;
import com.wechat.detal.common.util.CommonUtils;
import com.wechat.detal.common.util.JdkDesUtil;
import com.wechat.detal.config.ReturnDict;
import com.wechat.detal.config.ReceiveDict;
import com.wechat.detal.handler.SystemContentHandler;
import com.wechat.detal.handler.WechatContentHandler;
import com.wechat.detal.list.ResultStream;

/**
 * @author QiuMingJie
 * @date 2020-02-20 17:58
 * @description 接收和传入的dto, 总父类
 */
public class MessageStreamDto {

    public static ResultStream resultMessageStreamDto = new ResultStream();

    private String messageType;

    private String expand;

    private String expand1;

    private String expand2;

    private String expand3;

    private String expand4;

    private String expand5;

    private String expand6;

    private String text;

    public MessageStreamDto(String messageType, String expand, String expand1) {
        this.messageType = messageType;
        this.expand = expand;
        this.expand1 = expand1;
    }

    public MessageStreamDto(String messageType, String expand, String expand1, String expand2, String expand3, String expand4) {
        this.messageType = messageType;
        this.expand = expand;
        this.expand1 = expand1;
        this.expand2 = expand2;
        this.expand3 = expand3;
        this.expand4 = expand4;
    }

    @Override
    public String toString() {
        return messageType + ReturnDict.IN_ORDER +
                expand + ReturnDict.IN_ORDER +
                expand1 + ReturnDict.IN_ORDER +
                expand2 + ReturnDict.IN_ORDER +
                expand3 + ReturnDict.IN_ORDER +
                expand4 + ReturnDict.IN_ORDER +
                expand5 + ReturnDict.IN_ORDER +
                expand6;
    }

    public MessageStreamDto() {
    }

    public MessageStreamDto(String messageType) {
        this.messageType = messageType;
    }

    public String print() {
        return "MessageStreamDto{" +
                "messageType='" + messageType + '\'' +
                ", expand='" + expand + '\'' +
                ", expand1='" + expand1 + '\'' +
                ", expand2='" + expand2 + '\'' +
                ", expand3='" + expand3 + '\'' +
                ", expand4='" + expand4 + '\'' +
                ", expand5='" + expand5 + '\'' +
                ", expand6='" + expand6 + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    /**
     * 解密 调用，回参
     *
     * @return
     */
    public ResultStream des() {
        MessageStreamDto parse = JSONObject.parseObject(JdkDesUtil.jdkDESRe(this.text), this.getClass());
        if (CommonUtils.notEmpty(parse)) {
            if (parse.getMessageType().startsWith(ReceiveDict.WECHAT_HEAD)) {
                new WechatContentHandler().dispatcher(parse);
            }
            if (parse.getMessageType().startsWith(ReceiveDict.SYS_HEAD)) {
                new SystemContentHandler().dispatcher(parse);
            }
            System.out.println("收到   "+parse.print());
        }
        return resultMessageStreamDto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1;
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2;
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3;
    }

    public String getExpand4() {
        return expand4;
    }

    public void setExpand4(String expand4) {
        this.expand4 = expand4;
    }

    public String getExpand5() {
        return expand5;
    }

    public void setExpand5(String expand5) {
        this.expand5 = expand5;
    }

    public String getExpand6() {
        return expand6;
    }

    public void setExpand6(String expand6) {
        this.expand6 = expand6;
    }

}
