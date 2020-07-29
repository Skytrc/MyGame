package com.fung.server.gameserver.content.domain.team;

import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.util.Uuid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 玩家离线离开队伍 队长功能 踢人功能
 * 存储线上队伍容器
 * @author skytrc@163.com
 * @date 2020/7/2 20:51
 */
@Component
public class StoredTeam {

//    private Map<String, List<Player>> teamMap;

    private Map<String, Map<Integer, Player>> teamMap;

    public final static int TEAM_CAPACITY = 5;

    public final static int TEAM_LEADER = 1;

    public final static int TEAM_FULL = -1;

    public final static int TEAM_NOT_EXITS = -2;

    public StoredTeam() {
        teamMap = new ConcurrentHashMap<>();
    }

    /**
     * 创建队伍 返回队伍编号
     */
    public String createNewTeam(Player player) {
        String uuid = Uuid.createUuid();
        Map<Integer, Player> locationPlayerMap = new ConcurrentHashMap<>();
        locationPlayerMap.put(TEAM_LEADER, player);
        teamMap.put(uuid, locationPlayerMap);
        player.getTempStatus().setTeamId(uuid);
        player.getTempStatus().setTeamLocation(TEAM_LEADER);
        return uuid;
    }

    public int joinTeam(String teamId, Player player) {
        if (checkHasTeam(teamId)) {
            Map<Integer, Player> locationPlayerMap = teamMap.get(teamId);
            if (locationPlayerMap.size() <= TEAM_CAPACITY) {
                for (int i = 1; i <= TEAM_CAPACITY; i++) {
                    if (!locationPlayerMap.containsKey(i)){
                        locationPlayerMap.put(i, player);
                        // 临时状态挂载队伍id
                        player.getTempStatus().setTeamId(teamId);
                        player.getTempStatus().setTeamLocation(i);
                        return i;
                    }
                }
            }
            return TEAM_FULL;
        }
        return TEAM_NOT_EXITS;
    }

    /**
     * 检查队伍中是否有 队友（playerName）
     */
    public boolean hasPlayerWithTeam(Player player, String checkPlayerName) {
        String teamId = player.getTempStatus().getTeamId();
        if (teamId == null) {
            return false;
        }
        Map<Integer, Player> playerMap = teamMap.get(teamId);
        for (Player value : playerMap.values()) {
            if (value.getName().equals(checkPlayerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据队伍编号获取玩家信息
     */
    public Player getPlayerByLocation(String teamId, int location) {
        Map<Integer, Player> playerMap = teamMap.get(teamId);
        if (playerMap == null) {
            return null;
        }
        return playerMap.get(location);
    }

    /**
     * 离开队伍
     */
    public boolean leaveTeam(Player player) {
        String teamId = player.getTempStatus().getTeamId();
        if (teamId == null) {
            return false;
        }
        teamMap.get(teamId).remove(player.getTempStatus().getTeamLocation());
        checkAndRemoveEmptyTeam(teamId);
        player.getTempStatus().setTeamId(null);
        return true;
    }

    /**
     * 检查是否为空并移除空队伍
     */
    public void checkAndRemoveEmptyTeam(String teamId) {
        Map<Integer, Player> playerMap = teamMap.get(teamId);
        if (playerMap.isEmpty()) {
            teamMap.remove(teamId);
        }
    }

    public boolean checkHasTeam(String teamId) {
        return teamMap.containsKey(teamId);
    }

    public List<Player> getAllPlayerByTeamId(String teamId) {
        return new ArrayList<>(teamMap.get(teamId).values());
    }

    public Map<String, Map<Integer, Player>> getTeamMap() {
        return teamMap;
    }

    public void setTeamMap(Map<String, Map<Integer, Player>> teamMap) {
        this.teamMap = teamMap;
    }
}
