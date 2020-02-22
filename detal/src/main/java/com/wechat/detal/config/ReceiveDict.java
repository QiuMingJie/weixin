package com.wechat.detal.config;

/**
 * @author QiuMingJie
 * @date 2020-02-21 14:34
 * @description 接收时的字典
 * <p>
 * 状态码	意思	数据
 * 00000	系统通知-连接服务器
 * 00001	系统通知-发送二维码	二维码地址
 * 00002	系统通知-登录成功	id	昵称
 * 00003	系统通知-退出成功	id	昵称
 * 00004	系统通知-发送二维码
 * 00005	系统通知-断开服务器
 * <p>
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
 */
public class ReceiveDict {

    public static final String SYS_HEAD = "0";
    public static final String WECHAT_HEAD = "1";
    public static final String SYS_CONNECT = "00000";
    public static final String SYS_SENDMA = "00001";
    public static final String SYS_LOGIN_SUCCESS = "00002";
    public static final String SYS_QUIT_SUCCESS = "00003";
    public static final String SYS_DISCONNECT = "00005";
    public static final String WE_LOGIN_SUCCESS = "10000";
    public static final String WE_QUIT_SUCCESS = "10001";
    public static final String WE_UNKNOWN_MES = "10003";
    public static final String WE_FRIEND_INIT = "10100";
    public static final String WE_FRIEND_REMSG = "10101";
    public static final String WE_FRIEND_ADD = "10102";
    public static final String WE_FRIEND_REMONEY = "10103";
    public static final String WE_FIREND_REMONEY = "10104";
    public static final String WE_FRIEND_SUCCMONEY = "10105";
    public static final String WE_FRIEND_FAILMONEY = "10106";
    public static final String WE_FRIEND_FAILMSG = "10107";
    public static final String WE_CHAT_INIT = "10200";
    public static final String WE_CHAT_REMSG = "10201";
    public static final String WE_ACCOUNTS_INIT = "10300";
    public static final String WE_ACCOUNTS_REMSG = "10301";
    public static final String WE_SEND_RECOVER = "19900";


}
