package com.fung.server.gameserver.content.entity;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 12:35
 */
@Entity
@Table(name = "task_record")
public class TaskRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "task_id")
    private int taskId;

    /**
     * 任务进度
     */
    @Column(name = "task_progress")
    private int taskProgress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(int taskProgress) {
        this.taskProgress = taskProgress;
    }
}
