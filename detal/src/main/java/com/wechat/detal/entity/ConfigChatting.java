package com.wechat.detal.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author QiuMingJie
 * @date 2020-02-16 14:51
 * @description
 */
@Table(name = "ConfigChatting")
@Entity
public class ConfigChatting extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String chattingId;

    public String getChattingId() {
        return chattingId;
    }

    public void setChattingId(String chattingId) {
        this.chattingId = chattingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
