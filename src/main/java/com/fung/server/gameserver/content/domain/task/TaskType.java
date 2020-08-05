                                                                                                                                      package com.fung.server.gameserver.content.domain.task;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 14:52
 */
public enum TaskType {

    /**
     * 任务类型
     */
    KILL_MONSTER("kill_monster", 0),

    BUY_ITEMS("buy_item", 1),

    MOVE("move", 2),

    USE_ITEMS("use_items", 3),
    ;

    private String typeName;

    private int type;

    TaskType(String typeName, int type) {
        this.type = type;
        this.typeName = typeName;
    }
}
