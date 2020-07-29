package com.fung.server.gameserver.content.domain.team;

/**
 * @author skytrc@163.com
 * @date 2020/7/3 11:30
 */
public interface TeamReturnMessage {

    String TEAM_NOT_EXISTS = "队伍不存在";

    String JOIN_TEAM_SUCCESS = "成功加入队伍";

    String TEAM_FULL = "队伍已满";

    String SUCCESSFULLY_LEAVE_THE_TEAM = "成功离开队伍";

    String PLAYER_HAS_NO_TEAM = "玩家不在队伍内";

    String HAS_TEAM = "玩家已加入队伍中";
}
