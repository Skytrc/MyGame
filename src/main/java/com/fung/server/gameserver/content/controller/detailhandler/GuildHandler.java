package com.fung.server.gameserver.content.controller.detailhandler;

import com.fung.server.gameserver.content.entity.guild.Guild;
import com.fung.server.gameserver.content.service.guild.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 18:14
 */
@Component
public class GuildHandler extends BaseInstructionHandler{

    @Autowired
    private GuildService guildService;

    @Override
    public String handler(List<String> ins) throws InterruptedException {
        String remove = ins.remove(0);
        switch (remove) {
            case ("create"):
                return createNewGuild(ins);
            case ("join"):
                return joinGuild(ins);
            case ("checkapplicant"):
                return checkApplicant();
            case ("allow"):
                return allowJoinIn(ins);
            case ("deny"):
                return denyJoinIn(ins);
            case ("find"):
                return findGuildInfo(ins);
            case ("topten"):
                return topTenGuildInfo();
            case ("leave"):
                return leaveGuild(ins);
            case ("disband"):
                return disbandGuild(ins);
            case ("update"):
                return updateMemberPosition(ins);
            case ("kick"):
                return kickMember(ins);
            case ("transfer"):
                return transferPresident(ins);
            default: return "\n公会指令错误";
        }
    }

    public String createNewGuild(List<String> ins) {
        if (ins.size() == 0) {
            return "\n创建公会命令错误";
        }
        return guildService.createGuild(getChannelId(), ins.remove(0));
    }

    public String joinGuild(List<String> ins) {
        if (ins.size() == 0) {
            return "\n加入公会命令错误";
        }
        return guildService.joinGuild(getChannelId(), ins.remove(0));
    }

    public String checkApplicant() {
        return guildService.checkApplicant(getChannelId());
    }

    public String allowJoinIn(List<String> ins) {
        if (ins.size() == 0) {
            return "\n允许加入公会命令错误";
        }
        return guildService.allowJoinInGuild(getChannelId(), ins.remove(0));
    }

    public String denyJoinIn(List<String> ins) {
        if (ins.size() == 0) {
            return "\n拒绝加入公会命令错误";
        }
        return guildService.denyApplicant(getChannelId(), ins.remove(0));
    }

    public String findGuildInfo(List<String> ins) {
        if (ins.size() == 0) {
            return "\n查看公会命令错误";
        }
        return guildService.denyApplicant(getChannelId(), ins.remove(0));
    }

    public String topTenGuildInfo() {
        return guildService.getTopTenGuildInfo(getChannelId());
    }

    public String leaveGuild(List<String> ins) {
        if (ins.size() == 0) {
            return "\n离开公会命令错误";
        }
        return guildService.leaveGuild(getChannelId(), ins.remove(0));
    }

    public String disbandGuild(List<String> ins) {
        if (ins.size() == 0) {
            return "\n解散公会命令错误";
        }
        return guildService.disbandGuild(getChannelId(), ins.remove(0));
    }

    public String updateMemberPosition(List<String> ins) {
        if (ins.size() < 2) {
            return "\n公会成员职位调动命令错误";
        }
        return guildService.updateMemberPosition(getChannelId(), ins.remove(0), ins.remove(0));
    }

    public String kickMember(List<String> ins) {
        if (ins.size() == 0) {
            return "\n公会踢人命令错误";
        }
        return guildService.kickMember(getChannelId(), ins.remove(0));
    }

    public String transferPresident(List<String> ins) {
        if (ins.size() == 0) {
            return "\n转移会长命令错误";
        }
        return guildService.transferPresident(getChannelId(), ins.remove(0));
    }
}
