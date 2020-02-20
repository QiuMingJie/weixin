package com.wechat.detal.dto;

import com.wechat.detal.inter.Invoke;

/**
 * @author QiuMingJie
 * @date 2020-02-20 17:58
 * @description 接收和传入的dto, 总父类
 */
public class MessageStreamDto implements Invoke {

    private String messageType;

    private String expand;

    private String expand1;

    private String expand2;

    private String expand3;

    private String expand4;

    private String expand5;

    private String expand6;

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

    @Override
    public Object invoke() {
        return null;
    }
}
