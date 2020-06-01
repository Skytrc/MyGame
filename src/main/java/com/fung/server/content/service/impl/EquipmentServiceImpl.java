package com.fung.server.content.service.impl;

import com.fung.server.content.dao.EquipmentDao;
import com.fung.server.content.entity.Equipment;
import com.fung.server.content.service.EquipmentService;
import com.fung.server.content.util.playerutil.GoodUtil;
import com.fung.server.content.util.playerutil.PlayerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 16:36
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    GoodUtil goodUtil;

    @Autowired
    PlayerUtil playerUtil;

    @Autowired
    EquipmentDao equipmentDao;

    @Override
    public void createNewEquipment(String channelId, int equipmentId) {
        Equipment equipment = goodUtil.getRandomValueEquipment(equipmentId);
        equipment.setPlayerId(playerUtil.getCurrentPlayer(channelId).getUuid());
        equipmentDao.updateEquipment(equipment);

    }
}
