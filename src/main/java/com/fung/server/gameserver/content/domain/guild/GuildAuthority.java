package com.fung.server.gameserver.content.domain.guild;

import com.fung.server.gameserver.content.entity.guild.Guild;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/7/31 18:56
 */
public enum GuildAuthority {


    /**
     * 权限
     */
    GET_MONEY,

    KICK,

    DELETE,

    UPDATE_MEMBER,

    PERMIT,

    TRANSFER,

    EDIT;

    public List<GuildAuthority> getGuildAuthority(GuildPosition guildPosition) {
        List<GuildAuthority> authorities = new ArrayList<>();
        switch (guildPosition) {
            case ORDINARY_MEMBER:
                return authorities;
            case ELITE:
                authorities.add(GET_MONEY);
                return authorities;
            case VICE_PRESIDENT:
                authorities.add(KICK);
                authorities.add(DELETE);
                authorities.add(UPDATE_MEMBER);
                authorities.add(PERMIT);
                authorities.add(EDIT);
                return authorities;
            case PRESIDENT:
                authorities.add(TRANSFER);
                return authorities;
            default: return null;
        }
    }

}
