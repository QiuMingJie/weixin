package com.wechat.detal.handler;

import com.wechat.detal.dto.MessageStreamDto;

/**
 * @author QiuMingJie
 * @date 2020-02-21 14:31
 * @description 处理系统消息
 * 00000	系统通知-连接服务器
 * 00001	系统通知-发送二维码	二维码地址
 * 00002	系统通知-登录成功	id	昵称
 * 00003	系统通知-退出成功	id	昵称
 * 00004	系统通知-发送二维码
 * 00005	系统通知-断开服务器
 */
public class SystemContentHandler {

    public void dispatcher(MessageStreamDto messageStreamDto) {

    }
}
