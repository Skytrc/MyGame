package com.fung.server.gameserver.content.domain.reward;

/**
 * 奖励类型
 * @author skytrc@163.com
 * @date 2020/8/5 15:20
 */
public enum RewardType {

    /**
     * 奖励类型
     */
    MONEY("money", 0),

    GOOD("good", 1),

    EXP("exp", 2);

    private String name;

    private int type;

    RewardType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public static RewardType getRewardType(int i) {
        for (RewardType value : RewardType.values()) {
            if (value.type == i) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
