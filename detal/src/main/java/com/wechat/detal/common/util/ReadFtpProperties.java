package com.wechat.detal.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ReadFtpProperties
 * 读取属性文件
 *
 * @author yanghua
 * @date 2019/9/2
 */
public class ReadFtpProperties {
    private InputStream is;
    private Properties properties;

    public ReadFtpProperties() {
        is = this.getClass().getResourceAsStream("/ftpconfig.properties");// 将配置文件读入输入流中
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            System.out.println("配置文件不存在..");
            e.printStackTrace();
        } finally {

            if (null != is) {

                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("关闭流失败..");
                    e.printStackTrace();
                }
            }

        }

    }

    public String getIp() {// 获取ftp服务器的ip地址
        return properties.getProperty("ftpIp");

    }

    public String getPort() {// 获取ftp服务器的端口
        return properties.getProperty("ftpPort");

    }

    public String getUser() {// 获取ftp登录用户名
        return properties.getProperty("ftpUser");

    }

    public String getPwd() {// 获取ftp服务器的登录密码
        return properties.getProperty("ftpPwd");

    }

    public String getRemotePath() {// 获取ftp服务器的存放文件的目录
        return properties.getProperty("ftpRemotePath");

    }

}
