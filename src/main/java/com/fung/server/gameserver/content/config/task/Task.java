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

    /**
     * 任务类型
     */
    private int taskType;

    /**
     * 奖励类型
     */
    private int rewardType;


    /**
     * 最大进度
     */
    private int maxTaskProgress;

    /**
     * 任务进度
     */
    private int taskProgress;

    /**
     * 是否可重复
     */
    private boolean repeatable;

    /**
     * 等级限制
     */
    private int rankLimited;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public int getRankLimited() {
        return rankLimited;
    }

    public void setRankLimited(int rankLimited) {
        this.rankLimited = rankLimited;
    }

    public int getMaxTaskProgress() {
        return maxTaskProgress;
    }

    public void setMaxTaskProgress(int maxTaskProgress) {
        this.maxTaskProgress = maxTaskProgress;
    }

    public int getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(int taskProgress) {
        this.taskProgress = taskProgress;
    }
}
