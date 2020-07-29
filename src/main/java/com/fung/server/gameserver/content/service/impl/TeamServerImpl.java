package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.team.StoredTeam;
import com.fung.server.gameserver.content.domain.team.TeamMessageWrapper;
import com.fung.server.gameserver.content.domain.team.TeamReturnMessage;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/3 11:16
 */
@Component
public class TeamServerImpl implements TeamService {

    @Autowired
    private StoredTeam storedTeam;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    @Autowired
    private TeamMessageWrapper messageWrapper;

    @Override
    public String joinInTeam(String teamId, String channelId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        int teamLocation = storedTeam.joinTeam(teamId, onlinePlayer.getPlayerByChannelId(channelId));
        if (teamLocation == StoredTeam.TEAM_FULL) {
            return TeamReturnMessage.TEAM_FULL;
        } else if (teamLocation == StoredTeam.TEAM_NOT_EXITS) {
            return TeamReturnMessage.TEAM_NOT_EXISTS;
        }
        List<Player> players = storedTeam.getAllPlayerByTeamId(teamId);
        // 通知所有的队友
        for (Player player1 : players) {
            String teamMemberChannelId = onlinePlayer.getChannelIdByPlayerId(player1.getUuid());
            writeMessage2Client.writeMessage(teamMemberChannelId, messageWrapper.showNewTeamMember(player.getPlayerName(), teamLocation));
        }
        // 返回消息给当事人
        return messageWrapper.showTeamMessage(players, teamId, teamLocation);
    }

    @Override
    public String leaveTeam(String channelId) {
        if (storedTeam.leaveTeam(onlinePlayer.getPlayerByChannelId(channelId))){
            return TeamReturnMessage.SUCCESSFULLY_LEAVE_THE_TEAM;
        }
        return TeamReturnMessage.PLAYER_HAS_NO_TEAM;
    }

    @Override
    public String createTeam(String channelId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        if (player.getTempStatus().getTeamId() != null) {
            return TeamReturnMessage.HAS_TEAM;
        }
        String teamId = storedTeam.createNewTeam(player);
        return messageWrapper.showNewTeam(teamId);
    }

    @Override
    public String teamMessage(String channelId) {
        Player player = onlinePlayer.getPlayerByChannelId(channelId);
        String teamId = player.getTempStatus().getTeamId();
        if (teamId == null) {
            return TeamReturnMessage.PLAYER_HAS_NO_TEAM;
        }
        List<Player> players = storedTeam.getAllPlayerByTeamId(teamId);
        return messageWrapper.showTeamMessage(players, teamId, player.getTempStatus().getTeamLocation());
    }
}
