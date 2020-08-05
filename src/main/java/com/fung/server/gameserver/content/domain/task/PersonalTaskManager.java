package com.fung.server.gameserver.content.domain.task;

import com.fung.server.gameserver.content.config.task.Task;

import java.util.Map;

/**
 * 个人任务管理器
 * @author skytrc@163.com
 * @date 2020/8/5 17:06
 */
public class PersonalTaskManager {

    private Map<Integer, Task> taskInProcessMap;

    public void addTask(Task task) {
        taskInProcessMap.put(task.getId(), task);
    }

    public void finishTask(int taskId) {
        taskInProcessMap.remove(taskId);
    }

    public void getTask(int taskId) {
        taskInProcessMap.get(taskId);
    }
}
