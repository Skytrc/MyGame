package com.fung.server.gameserver.content.dao;

import com.fung.server.gameserver.content.entity.TaskRecord;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 12:41
 */
public interface TaskRecordDao {

    void insertOrUpdate(TaskRecord taskRecord);

    List<TaskRecord> findTaskRecordByPlayerId(String playerId);
}
