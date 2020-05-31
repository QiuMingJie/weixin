package com.wechat.detal.dao;

import com.wechat.detal.entity.conofig.ConfigChatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author QiuMingJie
 * @date 2020-02-18 12:18
 * @description
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigChatting, String> {

}
