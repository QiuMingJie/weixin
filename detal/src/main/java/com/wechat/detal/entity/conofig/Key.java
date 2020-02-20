package com.wechat.detal.entity.conofig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wechat.detal.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author QiuMingJie
 * @date 2020-02-16 14:56
 * @description 认证表,用来存储认证码的基本信息
 */
@Table(name = "KEY")
public class Key extends BaseEntity {

    /**
     * 认证码
     */
    @Id
    private String key;

    /**
     * 认证类型
     * 0是默认类型
     * 1
     */
    @Column(name ="KEY_TYPE", columnDefinition="varchar(255) default '0'")
    private String keyType;

    /**
     * 微信id
     */
    private String wechatId;

    /**
     * 用户当前的阶段
     */
    private Integer stage;

    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date usefulLife;

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(Integer adminNum) {
        this.adminNum = adminNum;
    }

    private Integer adminNum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public Date getUsefulLife() {
        return usefulLife;
    }

    public void setUsefulLife(Date usefulLife) {
        this.usefulLife = usefulLife;
    }
}
