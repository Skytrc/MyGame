package com.fung.server.gameserver.content.domain.team;

import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 队长功能 踢人功能
 * 存储线上队伍容器
 * @author skytrc@163.com
 * @date 2020/7/2 20:51
 */
@Component
public class StoredTeam {

    private Map<String, List<Player>> teamMap;

    /**
     * 创建队伍 返回队伍编号
     */
    public String createNewTeam(Player player) {
        String uuid = Uuid.createUuid();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        teamMap.put(uuid, players);
        return uuid;
    }

    public void joinTeam(String teamId, Player player) {
        teamMap.get(teamId).add(player);
    }

    public void leaveTeam(String teamId, Player player) {
        teamMap.get(teamId).remove(player);
    }

    public void checkAndRemoveEmptyTeam(String teamId) {
        List<Player> players = teamMap.get(teamId);
        if (players.isEmpty()) {
            teamMap.remove(teamId);
        }
    }

    /**
     * 获取后需要判断是否为空
     */
    public List<Player> getAllPlayerByTeamId(String teamId) {
        return teamMap.get(teamId);
    }


    public Map<String, List<Player>>  getTeamMap() {
        return teamMap;
    }

    public void setTeamMap(Map<String, List<Player>>  teamMap) {
        this.teamMap = teamMap;
    }
}
