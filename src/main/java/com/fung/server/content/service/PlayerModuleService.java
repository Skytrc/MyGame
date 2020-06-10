package com.fung.server.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 10:21
 */
public interface PlayerModuleService {

    /**
     * 加载玩家其他信息（技能、装备、背包）
     * @param channelId 加载玩家信息
     */
    void playerModuleLoad(String channelId);
}
