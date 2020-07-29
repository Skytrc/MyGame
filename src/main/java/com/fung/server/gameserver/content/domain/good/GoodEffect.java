package com.fung.server.gameserver.content.domain.good;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.entity.Medicine;
import com.fung.server.gameserver.content.domain.mapactor.PlayerActor;
import com.fung.server.gameserver.content.entity.Good;
import com.fung.server.gameserver.content.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author skytrc@163.com
 * @date 2020/7/17 11:05
 */
@Component
public class GoodEffect {

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    /**
     * 一次性添加属性
     */
    public void medicineEffect(Good good, PlayerActor playerActor, String channelId) {
        Player player = playerActor.getPlayer();
        Medicine medicine = (Medicine) good;
        if (medicine.getLastTime() != 0) {
            medicineEffectBuff(medicine, playerActor, 0, channelId);
        } else if(medicine.getEndTime() != 0) {
            medicineEffectEnd(medicine, playerActor, channelId);
        }
        addAttr(medicine, player, channelId);
    }

    /**
     * 持续添加属性
     */
    public void medicineEffectBuff(Medicine medicine, PlayerActor playerActor, int time, String channelId){
        Player player = playerActor.getPlayer();
        if (time <= medicine.getLastTime()) {
            int finalTime = time + 1;
            writeMessage2Client.writeMessage(channelId, " 持续: " + medicine.getLastTime());
            addAttr(medicine, player, channelId);
            playerActor.schedule(gameMapActor1 -> {
                medicineEffectBuff(medicine, playerActor, finalTime, channelId);
            }, 1, TimeUnit.SECONDS);
        }

    }

    /**
     * 一段时间添加属性
     */
    public void medicineEffectEnd(Medicine medicine, PlayerActor playerActor, String channelId) {
        addAttrWithTime(medicine, playerActor, channelId);
    }

    /**
     * 临时增加属性
     */
    public void addAttrWithTime(Medicine medicine, PlayerActor playerActor, String channelId){
        Player player = playerActor.getPlayer();
        if (medicine.getPlusAttack() != 0) {
            player.setAttackPower(player.getAttackPower() + medicine.getPlusAttack());
            playerActor.schedule(playerActor1 -> {
                player.setAttackPower(player.getAttackPower() - medicine.getPlusAttack());
            }, medicine.getEndTime(), TimeUnit.SECONDS);
            writeMessage2Client.writeMessage(channelId, "增加攻击力: " + medicine.getPlusAttack() + " " + medicine.getEndTime()
                    + " 秒后效果消失");
        }
        if (medicine.getPlusDefense() != 0) {
            player.setDefense(player.getDefense() + medicine.getPlusDefense());
            playerActor.schedule(playerActor1 -> {
                player.setDefense(player.getDefense() - medicine.getPlusDefense());
            }, medicine.getEndTime(), TimeUnit.SECONDS);
            writeMessage2Client.writeMessage(channelId, "增加防御力: " + medicine.getPlusDefense() + " " + medicine.getEndTime()
                    + " 秒后效果消失");
        }
    }

    /**
     * TODO 拆分
     * 添加属性，适合一次性添加和持续添加
     */
    public void addAttr(Medicine medicine, Player player, String channelId) {
        if (medicine.getPlusHp() != 0) {
            int maxHealthPoint = player.getMaxHealthPoint();
            int nowHealthPoint = player.getHealthPoint() + medicine.getPlusHp();
            player.setHealthPoint(Math.min(nowHealthPoint, maxHealthPoint));
            writeMessage2Client.writeMessage(channelId, "增加血量: " + medicine.getPlusHp() + " 目前血量: " + player.getHealthPoint());
        }
        if (medicine.getPlusMp() != 0) {
            int maxMagicPoint = player.getMaxMagicPoint();
            int nowMagicPoint = player.getMagicPoint();
            player.setMagicPoint(Math.min(maxMagicPoint, nowMagicPoint));
            writeMessage2Client.writeMessage(channelId, "增加蓝量: " + medicine.getPlusMp() + " 目前蓝量: " + player.getMagicPoint());
        }
        if (medicine.getPlusAttack() != 0) {
            player.setAttackPower(player.getAttackPower() + medicine.getPlusAttack());
            writeMessage2Client.writeMessage(channelId, "增加攻击力: " + medicine.getPlusAttack() + " 目前攻击力: " + player.getAttackPower());
        }
        if (medicine.getPlusDefense() != 0) {
            player.setDefense(player.getDefense() + medicine.getPlusDefense());
            writeMessage2Client.writeMessage(channelId, "增加防御力: " + medicine.getPlusDefense() + " 目前防御力: " + player.getDefense());
        }

    }
}
