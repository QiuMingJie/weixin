package com.wechat.detal.handler;

import com.wechat.detal.dto.MessageStreamDto;

/**
 * @author QiuMingJie
 * @date 2020-02-21 14:32
 * @description
 * 10000    微信通知-登录成功	id	昵称
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
 */
public class WechatContentHandler {

    public void dispatcher(MessageStreamDto messageStreamDto) {
//        WechatUtil.sendMsg(messageStreamDto.getExpand4(),"直接返回值<br>"+messageStreamDto.getExpand3());
//        WechatUtil.sendAdminMsg("你<br>好");
//        List<String> a=new ArrayList<>();
//        a.add("filehelper");
//        a.add(Config.adminWechatId1);
//        WechatUtil.sendMsg(a,"接口" );
//        WechatUtil.sendContent(00000"","22677170063@chatroom", "11");
//        WechatUtil.sendContent(Config.adminWechatId1,"吃惊",ReturnDict.SEND_XML,new String []{"file:///C:/Users/Qiu/Desktop/12.jpg","描述","https://jingyan.baidu.com/article/295430f12e7fe80c7e0050d0.html"});
    }
}
