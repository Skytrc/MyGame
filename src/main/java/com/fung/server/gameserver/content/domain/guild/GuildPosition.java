package com.fung.server.gameserver.content.domain.guild;

/**
 * 公会职位
 * @author skytrc@163.com
 * @date 2020/7/31 12:26
 */
public enum  GuildPosition {

    /**
     * 公会职位
     */
    PRESIDENT("会长", 0),

    VICE_PRESIDENT( "副会长", 1),

    ELITE("精英", 2),

    ORDINARY_MEMBER("普通成员", 3);

    private String name;

    private int position;

    GuildPosition(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public static GuildPosition getGuildPositionByName(String name) {
        for (GuildPosition value : GuildPosition.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
