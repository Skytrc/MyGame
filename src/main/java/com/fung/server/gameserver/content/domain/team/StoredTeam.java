package com.fung.server.gameserver.content.domain.team;

import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 玩家离线离开队伍 队长功能 踢人功能
 * 存储线上队伍容器
 * @author skytrc@163.com
 * @date 2020/7/2 20:51
 */
@Component
public class StoredTeam {

    private Map<String, List<Player>> teamMap;

    public final static int TEAM_CAPACITY = 5;

    public StoredTeam() {
        teamMap = new HashMap<>();
    }

    /**
     * 创建队伍 返回队伍编号
     */
    public String createNewTeam(Player player) {
        String uuid = Uuid.createUuid();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        teamMap.put(uuid, players);
        player.getTempStatus().setTeamId(uuid);
        return uuid;
    }

    public boolean joinTeam(String teamId, Player player) {
        if (checkHasTeam(teamId)) {
            List<Player> players = teamMap.get(teamId);
            if (players.size() < TEAM_CAPACITY) {
                players.add(player);
                // 临时状态挂载队伍id
                player.getTempStatus().setTeamId(teamId);
                return true;
            }
        }
        return false;
    }

    /**
     * 离开队伍
     */
    public boolean leaveTeam(Player player) {
        String teamId = player.getTempStatus().getTeamId();
        if (teamId == null) {
            return false;
        }
        teamMap.get(teamId).remove(player);
        checkAndRemoveEmptyTeam(teamId);
        player.getTempStatus().setTeamId(null);
        return true;
    }

    /**
     * 检查是否为空并移除空队伍
     */
    public void checkAndRemoveEmptyTeam(String teamId) {
        List<Player> players = teamMap.get(teamId);
        if (players.isEmpty()) {
            teamMap.remove(teamId);
        }
    }

    public boolean checkHasTeam(String teamId) {
        return teamMap.containsKey(teamId);
    }

    public List<Player> getAllPlayerByTeamId(String teamId) {
        return teamMap.get(teamId);
    }


    public Map<String, List<Player>> getTeamMap() {
        return teamMap;
    }

    public void setTeamMap(Map<String, List<Player>>  teamMap) {
        this.teamMap = teamMap;
    }
}
