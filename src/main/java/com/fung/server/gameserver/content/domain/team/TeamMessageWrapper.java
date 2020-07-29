package com.fung.server.gameserver.content.domain.team;

import com.fung.server.gameserver.content.entity.Player;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/3 11:24
 */
@Component
public class TeamMessageWrapper {

    public String showTeamMessage(List<Player> players, String teamId, int teamLocation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("队伍人数: ").append(players.size()).append("\n").append("队伍编号: ").append(teamId)
                .append(" 你的队内编号为").append(teamLocation).append("\n");
        for (Player player : players) {
            stringBuilder.append("名称: ").append(player.getPlayerName()).append("  队内编号为: ")
                    .append(player.getTempStatus().getTeamLocation());
        }
        return stringBuilder.toString();
    }

    public String showNewTeam(String teamId) {
        return "\n成功创建新的队伍，队名编号为: " + teamId + " 你是队长, 队内编号为: 1 ";
    }

    public String showNewTeamMember(String playName, int teamLocation) {
        return "\n新增队友: " + playName + " 队内编号: " + teamLocation;
    }
}
