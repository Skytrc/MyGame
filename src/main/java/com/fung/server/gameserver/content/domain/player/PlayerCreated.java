package com.fung.server.gameserver.content.domain.player;

import com.fung.server.gameserver.content.dao.EquipmentDao;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.dao.SkillDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.good.GoodCreatedFactory;
import com.fung.server.gameserver.content.domain.skill.SkillLoad;
import com.fung.server.gameserver.content.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 20:11
 */
@Component
public class PlayerCreated {

    @Autowired
    GoodCreatedFactory goodCreatedFactory;

    @Autowired
    SkillLoad skillLoad;

    /**
     * 创建人物（任务初始属性、背包、其他设置、技能等）
     */
    public Player createPlayer(String playerName, String password) {
        Player player = new Player();

        // 人物基础信息
        // TODO 使用配置
        player.setPlayerName(playerName);
        player.setPassword(password);
        player.setCreatedDate(System.currentTimeMillis());
        player.setMaxHealthPoint(100);
        player.setHealthPoint(100);
        player.setMaxMagicPoint(100);
        player.setMagicPoint(100);
        player.setAttackPower(10);
        player.setCriticalRate(0);
        player.setDefense(5);
        player.setStatus(1);
        player.setLevel(1);
        player.setInMapId(1);
        player.setInMapX(1);
        player.setInMapY(1);
        player.setEquipments(new ArrayList<>(5));

        return player;
    }

    public PlayerCommConfig playerCommConfigCreated(Player player) {
        // PlayerCommConfig
        PlayerCommConfig playerCommConfig = new PlayerCommConfig();
        playerCommConfig.setUuid(player.getUuid());
        playerCommConfig.setMaxBackpackGrid(50);
        playerCommConfig.setMoney(1000);
        player.setPlayerCommConfig(playerCommConfig);
        return playerCommConfig;
    }

    /**
     * 初始化玩家背包、技能，并插入数据库
     * @param player 玩家
     * @param goodDao 技能插入
     * @param equipmentDao 装备插入
     * @param skillDao 技能插入
     */
    public Player playerModuleCreated(Player player, GoodDao goodDao, EquipmentDao equipmentDao, SkillDao skillDao) {

        // 背包模块
        PersonalBackpack personalBackpack = new PersonalBackpack();
        personalBackpack.setMaxBackpackGrid(player.getPlayerCommConfig().getMaxBackpackGrid());
        List<Good> goods = goodCreatedFactory.newPlayerGoodCreated(player.getUuid());
        List<Equipment> equipments = goodCreatedFactory.newPlayerEquipmentCreated(player.getUuid());

        // 物品、武器放入背包
        for (Good good : goods) {
            personalBackpack.addGood(good);
        }
        for (Equipment equipment : equipments) {
            personalBackpack.addEquipment(equipment);
        }
        goods.forEach(goodDao::insertGood);
        equipments.forEach(equipmentDao::insertEquipment);

        player.setPersonalBackpack(personalBackpack);

        // 技能模块
        List<Skill> skills = skillLoad.newPlayerSkillCreated(player.getUuid());
        skills.forEach(skillDao::insertSkill);
        player.setSkills(skills);

        return player;
    }
}
