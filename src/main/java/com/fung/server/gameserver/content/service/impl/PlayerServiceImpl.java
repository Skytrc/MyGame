package com.fung.server.gameserver.content.service.impl;

import com.fung.server.gameserver.cache.mycache.PlayerCache;
import com.fung.server.gameserver.content.config.good.equipment.EquipmentCreated;
import com.fung.server.gameserver.content.config.good.equipment.EquipmentType;
import com.fung.server.gameserver.content.config.manager.EquipmentCreatedManager;
import com.fung.server.gameserver.content.config.manager.GoodManager;
import com.fung.server.gameserver.content.config.manager.MapManager;
import com.fung.server.gameserver.content.config.manager.SkillManager;
import com.fung.server.gameserver.content.dao.EmailDao;
import com.fung.server.gameserver.content.dao.EquipmentDao;
import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.dao.SkillDao;
import com.fung.server.gameserver.content.domain.backpack.PersonalBackpack;
import com.fung.server.gameserver.content.domain.buff.UnitBuffManager;
import com.fung.server.gameserver.content.domain.calculate.PlayerValueCalculate;
import com.fung.server.gameserver.content.domain.email.MailBox;
import com.fung.server.gameserver.content.domain.player.PlayerCreated;
import com.fung.server.gameserver.content.domain.skill.UnitSkillManager;
import com.fung.server.gameserver.content.entity.*;
import com.fung.server.gameserver.content.service.PlayerService;
import com.fung.server.gameserver.content.domain.player.OnlinePlayer;
import com.fung.server.gameserver.content.domain.player.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/5/18 21:05
 */
@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private PlayerCreated playerCreated;

    @Autowired
    private PlayerInfo playerInfo;

    @Autowired
    private PlayerValueCalculate playerValueCalculate;

    @Autowired
    private OnlinePlayer onlinePlayer;

    @Autowired
    private MapManager mapManager;

    @Autowired
    private GoodManager goodManager;

    @Autowired
    private EquipmentCreatedManager createdManager;

    @Autowired
    private SkillManager skillManager;

    @Autowired
    private SkillDao skillDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private EmailDao emailDao;

    @Override
    public String register(String playerName, String password) {
        Player player = playerCache.getPlayerByPlayerName(playerName);
        if (player != null) {
            return "角色已存在，请重新注册";
        }

        // 角色新建模块
        Player newPlayer = playerCreated.createPlayer(playerName, password);
        playerCache.createPlayer(newPlayer);

        // 插入数据库后再重新获取player,为了获取uuid
        Player player1 = playerCache.getPlayerByPlayerName(newPlayer.getPlayerName());
        // 新建、插入、关联config
        PlayerCommConfig playerCommConfig = playerCreated.playerCommConfigCreated(player1);
        playerCache.insertPlayerCommConfig(playerCommConfig);
        player1.setPlayerCommConfig(playerCommConfig);
        playerCreated.playerModuleCreated(player1, goodDao, equipmentDao, skillDao);

        return "角色创建完毕";
    }

    @Override
    public String login(String playerName, String password, String channelId) {
        Player player = playerCache.getPlayerByPlayerName(playerName);
        if (player == null) {
            return "不存在该名玩家";
        }
        if (!player.getPassword().equals(password)) {
            return "角色名或密码不正确";
        }
        // 把登录的玩家放在onlinePlayerMap中，与channel捆绑，方便操作
        Player playerLogin = playerCache.getPlayerByPlayerName(playerName);
        // 更新登录日期
        playerLogin.setLoginDate(System.currentTimeMillis());

        // 挂载
        playerLoad(player, player.getPlayerCommConfig(), channelId);

        playerValueCalculate.calculatePlayerBaseValue(player);

        // onlinePlayer -> 指向PlayerCache的玩家实体
        onlinePlayer.addPlayer(playerLogin, channelId);
        // 地图Map捆绑线上玩家
        mapManager.getMapByMapId(playerLogin.getInMapId()).addPlayer(playerLogin);
        // TODO 加上邮件检测
        return "登录成功：\n " + playerInfo.showPlayerInfo(player);
    }

    @Override
    public String logout(String channelId) {
        if (onlinePlayer.getPlayerMap().containsKey(channelId)) {
            Player player = onlinePlayer.removePlayer(channelId);
            // 地图 在线Map 移除该玩家
            playerInfo.getCurrentPlayerMap(channelId).removePlayer(player);
            return "登出成功";
        }
        return "没有角色登录";
    }

    public void playerLoad(Player player, PlayerCommConfig playerCommConfig, String channelId) {
        // 挂载技能
        if (player.getSkillManager() == null) {
            UnitSkillManager unitSkillManager = new UnitSkillManager();
            List<Skill> skills = skillDao.findSkillsByPlayerId(player.getUuid());
            List<Skill> newSkill = new ArrayList<>();
            for (Skill skill : skills) {
                newSkill.add(skillManager.personalSkillInit(skill));
            }
            // 初始化每个技能的信息
            unitSkillManager.updateSkill(newSkill);
            player.setSkillManager(unitSkillManager);
        }
        // Buff状态
        UnitBuffManager unitBuffManager = new UnitBuffManager();
        // 双向绑定
        player.setBuffManager(unitBuffManager);
        unitBuffManager.setUnit(player);

        Map<Integer, EquipmentCreated> equipmentCreatedMap = createdManager.getEquipmentCreatedMap();
        // 挂载装备
        if (player.getEquipments() == null) {
           List<Equipment> equipmentList = new ArrayList<>(PlayerCommConfig.playerBodyEquipmentNum);
           // 创建新的空装备（用于占位）
            for (int i = 0; i < PlayerCommConfig.playerBodyEquipmentNum; i++) {
                equipmentList.add(new Equipment());
            }
            List<Equipment> equipments = equipmentDao.findEquipmentsByPlayerId(player.getUuid());
            for (Equipment equipment : equipments) {
                setEquipmentType(equipment, equipmentCreatedMap);
                // 放入EquipmentList 中
                int position = equipment.getType().getPosition();
                equipmentList.set(position, equipment);
            }
            player.setEquipments(equipmentList);
        }
        // 背包
        if (player.getPersonalBackpack() == null) {
            List<Good> goods = goodDao.getGoodByPlayerId(player.getUuid());
            List<Equipment> equipmentList = goodDao.findBackpackEquipment(player.getUuid());

            PersonalBackpack personalBackpack = new PersonalBackpack();
            // 设置背包最大格子数
            personalBackpack.setMaxBackpackGrid(playerCommConfig.getMaxBackpackGrid());
            Map<Integer, Good> backpack = personalBackpack.getBackpack();
            // 背包物品放入背包中，装备放入equipmentMap中
            for (Good good : goods) {
                String[] goodInfoById = goodManager.getGoodInfo(good.getGoodId());
                good.setName(goodInfoById[GoodManager.GOOD_NAME]);
                good.setDescription(goodInfoById[GoodManager.GOOD_DESCRIPTION]);
                good.setValue(Integer.parseInt(goodInfoById[GoodManager.GOOD_VALUE]));
                good.setMaxStack(Integer.parseInt(goodInfoById[GoodManager.GOOD_MAX_STACKS]));
                backpack.put(good.getPosition(), good);
            }
            equipmentList.forEach(value -> {
                setEquipmentType(value, equipmentCreatedMap);
                backpack.put(value.getPosition(), value);
            });
            player.setPersonalBackpack(personalBackpack);
        }

        // playerTempStatus
        player.getTempStatus().setChannelId(channelId);

        // 挂载邮箱
        MailBox mailBox = new MailBox();
        // 初始化邮箱
        mailBox.mailBoxInit(emailDao.getAllRecipientEmailByPlayerId(player.getUuid()));
        // 初始化草稿箱
        mailBox.draftBoxInit(emailDao.getAllSendEmailByPlayerId(player.getUuid()));
        player.setMailBox(mailBox);
    }

    public void setEquipmentType(Equipment equipment, Map<Integer, EquipmentCreated> equipmentCreatedMap) {
        EquipmentCreated equipmentCreated = equipmentCreatedMap.get(equipment.getGoodId());
        String type = equipmentCreated.getType();
        equipment.setName(equipmentCreated.getName());
        equipment.setDescription(equipmentCreated.getDescription());
        equipment.setMaxStack(1);
        equipment.setValue(equipmentCreated.getValue());
        equipment.setType(EquipmentType.getEquipmentTypeByPartName(type));
    }

    public void skillInit(Skill skill) {

    }
}
