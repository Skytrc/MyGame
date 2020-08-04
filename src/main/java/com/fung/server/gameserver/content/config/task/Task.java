package com.fung.server.gameserver.content.config.task;

import com.fung.server.gameserver.excel2class.Model;

/**
 * @author skytrc@163.com
 * @date 2020/8/4 20:12
 */
public class Task implements Model {

    private int id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDescription;

    @Override
    public int getId() {
        return id;
    }
}
