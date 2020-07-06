package com.fung.server.gameserver.content.service;

import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/7/2 21:12
 */
@Component
public interface TeamService {

    /**
     * 根据队伍id加入队伍
     * @param teamId 队伍id
     * @param channelId channel id
     * @return 加入情况
     */
    String joinInTeam(String teamId, String channelId);

    /**
     * 离开队伍
     * @param channelId channel id
     * @return 离开情况
     */
    String leaveTeam(String channelId);

    /**
     * 创建队伍
     * @param channelId channel Id
     * @return 创建队伍情况
     */
    String createTeam(String channelId);

    /**
     * 返回队伍信息
     * @param channelId channel Id
     * @return 队伍人数、队友名字
     */
    String teamMessage(String channelId);
}
