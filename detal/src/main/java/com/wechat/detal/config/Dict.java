package com.wechat.detal.config;

/**
 * @author QiuMingJie
 * @date 2020-02-21 14:11
 * @description
 * 发送码    传参					意思
 * 00000						发送
 * 10100	id	信息				微信发送信息
 * 10101	id	路径				微信发送图片
 * 10102	id	路径				微信发送文件
 * 10103	id	路径				微信发送视频
 * 10104	id	id				微信发送名片
 * 10105	id	标题	图片	描述	链接	微信发送xml
 * 10200	id	单号				微信支付收款
 * 10300	v1	v2
 * 命令之间	!@#				命令内	$%^
 */
public class Dict {

    public static String sendMsg = "10100";

    public static String sendPhoto = "10101";

    public static String sendFile = "10102";

    public static String sendVideo = "10103";

    public static String sendCard = "10104";

    public static String sendXml = "10105";

    public static String acceptMoney = "10200";

    public static String acceptFriend = "10300";

}
