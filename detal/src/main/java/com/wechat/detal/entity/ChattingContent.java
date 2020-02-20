package com.wechat.detal.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author QiuMingJie
 * @date 2020-02-20 17:18
 * @description 保存聊天内容
 */
@Table
@Entity
public class ChattingContent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String senderChattingId;

    private String senderWechatId;

    private String receiverId;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderChattingId() {
        return senderChattingId;
    }

    public void setSenderChattingId(String senderChattingId) {
        this.senderChattingId = senderChattingId;
    }

    public String getSenderWechatId() {
        return senderWechatId;
    }

    public void setSenderWechatId(String senderWechatId) {
        this.senderWechatId = senderWechatId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
