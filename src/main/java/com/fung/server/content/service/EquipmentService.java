package com.fung.server.content.service;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 16:05
 */
public interface EquipmentService {

    /**
     * 创建武器对象
     * @param channelId 管道id,用于绑定player id
     * @param equipmentId 装备id
     */
    void createNewEquipment(String channelId, int equipmentId);
}
