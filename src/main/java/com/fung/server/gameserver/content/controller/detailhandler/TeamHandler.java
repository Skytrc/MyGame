package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/3 15:58
 */
@Component
public class TeamHandler extends BaseInstructionHandler{

    @Autowired
    private TeamService teamService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String s = ins.remove(0);
        switch (s) {
            case ("join") :
                return joinTeam(ins);
            case ("leave") :
                return leaveTeam();
            case ("create") :
                return create();
            case ("show") :
                return showMessage();
            default:
                return "队伍指令错误";
        }
    }

    public String joinTeam(List<String> ins) {
        String s = ins.remove(0);
        if ("".equals(s)) {
            return "请输入正确的队伍编号";
        }
        return teamService.joinInTeam(s, getChannelId());
    }

    public String leaveTeam() {
        return teamService.leaveTeam(getChannelId());
    }

    public String create() {
        return teamService.createTeam(getChannelId());

    }

    public String showMessage() {
        return teamService.teamMessage(getChannelId());
    }
}