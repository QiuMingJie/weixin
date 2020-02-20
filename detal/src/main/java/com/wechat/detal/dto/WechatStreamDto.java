package com.wechat.detal.dto;

/**
 * @author QiuMingJie
 * @date 2020-02-20 17:58
 * @description 接收和传入的dto,总父类
 */
public class WechatStreamDto {

    protected String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
