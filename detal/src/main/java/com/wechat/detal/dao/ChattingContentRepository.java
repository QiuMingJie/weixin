package com.wechat.detal.dao;

import com.wechat.detal.entity.ChattingContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author QiuMingJie
 * @date 2020-02-20 19:54
 * @description 聊天记录
 */
@Repository
public interface ChattingContentRepository extends JpaRepository<ChattingContent, String> {

    /**
     * 查询私聊的对话内容
     * @param senderId 发送者
     * @param receiveId 收到者
     * @param page 翻页
     * @return
     */
    public List<ChattingContent> findChattingContentsBySenderWechatIdOrReceiverId(String senderId, String receiveId,Pageable page);


    /**
     * 查询群聊信息
     * @param senderId
     * @param receiveId
     * @param page
     * @return
     */
    public List<ChattingContent> findChattingContentsBySenderChattingIdOrReceiverId(String senderId, String receiveId,Pageable page);



}
