package com.wechat.detal.entity.dict;

import com.wechat.detal.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author QiuMingJie
 * @date 2020-02-20 20:58
 * @description 认证码类型权限表
 */
@Table
@Entity
public class KeyTypeDict extends BaseEntity {
    @Id
    private String keyType;
}
