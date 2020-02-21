package com.wechat.detal.controller;

import com.wechat.detal.dto.MessageStreamDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiuMingJie
 * @date 2020-02-18 12:11
 * @description 接收调用的总入口
 * 传入
 * 状态码	意思	数据
 * 00000	系统通知-连接服务器
 * 00001	系统通知-发送二维码	二维码地址
 * 00002	系统通知-登录成功	id	昵称
 * 00003	系统通知-退出成功	id	昵称
 * 00004	系统通知-发送二维码
 * 00005	系统通知-断开服务器
 *
 * 10000	微信通知-登录成功	id	昵称
 * 10001	微信通知-退出成功	id	昵称
 * 10003	微信通知-未知消息
 * 10100	微信好友-初始化	索引	id	昵称	备注
 * 10101	微信好友-收到信息	索引	时间	状态	内容	 m_消息.ID1	m_消息.来自
 * 10102	微信好友-好友添加	id	昵称	请求消息	v1	v2
 * 10103	微信好友-转账请求	id	昵称	金额	单号
 * 10104	微信好友-确认收款	id	昵称	金额	单号
 * 10105	微信好友-收款成功	id	昵称	金额	单号
 * 10106	微信好友-收款失败	id	昵称	金额	单号
 * 10107	微信好友-消息发送失败
 * 10200	微信群聊-初始化	索引	id	昵称
 * 10201	微信群聊-收到信息	索引	时间	内容	 发送人id	m_消息.来自	群聊id
 * 10300	微信公众号-初始化	索引	id	昵称
 * 10301	微信公众号-收到信息
 * 19900	微信信息-发出信息	索引写死为-1	时间	内容	发送到达id	消息接受者昵称
 *
 *
 * 发送码	传参					意思
 * 00000						发送
 * 10100	id	信息				微信发送信息
 * 10101	id	路径				微信发送图片
 * 10102	id	路径				微信发送文件
 * 10103	id	路径				微信发送视频
 * 10104	id	id				微信发送名片
 * 10105	id	标题	图片	描述	链接	微信发送xml
 * 10200	id	单号				微信支付收款
 * 10300	v1	v2
 * !@#						$%^
 */
@RestController
@RequestMapping("/wechat")
public class AcceptController {
    @RequestMapping(value = "/msg", method = RequestMethod.POST)
    public String aa(@RequestBody MessageStreamDto messageStreamDto) {
        messageStreamDto.des();
        return messageStreamDto.invoke().toString();
    }
}
