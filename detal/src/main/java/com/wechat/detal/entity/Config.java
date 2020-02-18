package com.wechat.detal.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author QiuMingJie
 * @date 2020-02-16 14:51
 * @description
 */
@Table(name="config")
public class Config extends BaseEntity{

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
