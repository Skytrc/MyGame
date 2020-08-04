package com.fung.server.gameserver.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/7/30 9:54
 */
public interface PlayerOtherService {

    /**
     * 重生
     * @param channelId  channel Id
     * @return 重生情况
     */
    String rebirth(String channelId);
}
