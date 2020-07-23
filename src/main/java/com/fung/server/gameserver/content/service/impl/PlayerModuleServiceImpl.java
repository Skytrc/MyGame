package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.content.dao.EquipmentDao;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.dao.PlayerDao;
import com.fung.server.gameserver.content.dao.SkillDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import com.fung.server.gameserver.content.entity.Skill;
import com.fung.server.gameserver.content.service.PlayerModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 10:22
 */
@Component
public class PlayerModuleServiceImpl implements PlayerModuleService {

    @Autowired
    PlayerInfo playerInfo;

    @Autowired
    GoodDao goodDao;

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    SkillDao skillDao;

    @Override
    public void playerModuleLoad(String channelId) {
        Player currentPlayer = playerInfo.getCurrentPlayer(channelId);
        List<Good> goods = goodDao.getGoodByPlayerId(currentPlayer.getUuid());
        PersonalBackpack backpack = new PersonalBackpack();
        backpack.setMaxBackpackGrid(
                playerDao.getPlayerCommConfigByPlayerId(currentPlayer.getUuid())
                        .getMaxBackpackGrid());
        Map<Integer, Good> map = new HashMap<>(goods.size());
        for (Good good : goods) {
            map.put(good.getPosition(), good);
        }
        // 背包挂载
        backpack.setBackpack(map);
        currentPlayer.setPersonalBackpack(backpack);
        // 设置挂载
        List<Equipment> equipments = equipmentDao.findEquipmentsByPlayerId(currentPlayer.getUuid());
        currentPlayer.setEquipments(equipments);
        // 技能挂载
//        List<Skill> skills = skillDao.findSkillsByPlayerId(currentPlayer.getUuid());
//        currentPlayer.setSkills(skills);
    }
}
