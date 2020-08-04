package com.fung.server.gameserver.content.domain.buff;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.entity.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * buff管理
 * @author skytrc@163.com
 * @date 2020/7/22 10:51
 */
public class UnitBuffManager {

    /**
     * 用于记录生物身上的buff
     */
    private Map<Integer, Buff> buffMap;

    /**
     * 双向绑定
     */
    private Unit unit;


    /**
     * 是否可移动
     */
    private boolean moveLimited;

    /**
     * 是否可动
     */
    private boolean actionLimited;

    /**
     * 护盾值
     */
    private int shield;

    public UnitBuffManager() {
        buffMap = new HashMap<>();

    }

    /**
     * 添加buff
     */
    public void putOnBuff(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        trigger(buff, gameMapActor, writeMessage2Client);
//        int categoryId = buff.getBuffSpecies().getBufferCategory();
//        List<Buff> buffs;
//        // 原本没有该种类buff
//        if (!buffMap.containsKey(categoryId)) {
//            buffs = new ArrayList<>();
//            buffMap.put(categoryId, buffs);
//        } else {
//            // 检查是否存在一样的buff。是，检查是否可重叠&更新持续时间
//            buffs = buffMap.get(categoryId);
//            for (Buff buff1 : buffs) {
//                if (buff1.getId() == buff.getId()) {
//                    // 重置持续时间
//                    buff1.setLastTime(0);
//                    // 检查层数
//                    if (buff1.getLayer() + 1 < buff1.getMaxLastTime()) {
//                        buff1.setLayer(buff1.getLayer() + 1);
//                    }
//                    return;
//                }
//            }
//        }
//        buffs.add(buff);
//        // TODO buff计时开启
//        trigger(buff, channelId);
    }

    public void trigger(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        if (isInRange(BuffSpecies.MoveLimit, buff.getId())) {
            moveBuff(buff, gameMapActor, writeMessage2Client);
        } else if (isInRange(BuffSpecies.Dot, buff.getId())) {
            newDotCalculate(buff, gameMapActor, writeMessage2Client);
        } else if (isInRange(BuffSpecies.Shield, buff.getId())) {
            shieldJudgement(buff, gameMapActor, writeMessage2Client);
        } else if (isInRange(BuffSpecies.ActionLimit, buff.getId())) {
            actionBuff(buff, gameMapActor, writeMessage2Client);
        }
    }

    public boolean isInRange(BuffSpecies buffSpecies, int id) {
        return buffSpecies.getStart() <= id && id <= buffSpecies.getEnd();
    }

    /**
     * 移除buff(指dot buff)
     */
    public void removeBuff(Buff buff) {
        buffMap.remove(buff.getId());
    }

    /**
     * 护盾判断
     */
    public void shieldJudgement(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        if (shield < buff.getShield()) {
            shield = buff.getShield();
        }
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n获得新护盾,数值为: " + shield);
    }

    /**
     * 新的dot处理
     */
    public void newDotCalculate(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        int buffId = buff.getId();
        // 判断是否有同类buff
        if (buffMap.containsKey(buffId)) {
            Buff buff1 = buffMap.get(buffId);
            // 重置持续时间
            buff1.setLastTime(0);
            // 检查层数
            if (buff1.getLayer() + 1 < buff1.getMaxLayer()) {
                buff1.setLayer(buff1.getLayer() + 1);
            }
            buff = buff1;
        } else {
            buffMap.put(buffId, buff);
            buff.setLayer(1);
        }
        Buff finalBuff = buff;
        gameMapActor.addMessage(gameMapActor1 -> {
            dotCalculate(finalBuff, gameMapActor, writeMessage2Client);
        });
    }

    /**
     * 持续状态计算
     */
    public void dotCalculate(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        // 判断是否超过时间
        if (buff.getLastTime() > buff.getMaxLastTime()) {
            // 需要remove掉buff
            removeBuff(buff);
            return;
        }
        int layer = buff.getLayer();
        // 剩余时间
        int remainingTime = buff.getMaxLastTime() - buff.getLastTime();
        int minusHp = layer * buff.getDamagePerSecond();
        unit.setHealthPoint(Math.max(unit.getHealthPoint() - minusHp, 0));
        // 持续时间 + 1
        buff.setLastTime(buff.getLastTime() + 1);
        String res = "\n" + unit.getName() + " 身上buff: " + buff.getName()
                + " 层数: " + buff.getLayer() + (minusHp > 0 ? " 扣除: " : " 增加: ") + minusHp
                + " 剩余: " + remainingTime + "秒" + "\n剩余血量: " + unit.getHealthPoint();
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), res);
        // 加入地图线程队列
        gameMapActor.schedule(gameMapActor1 -> dotCalculate(buff, gameMapActor, writeMessage2Client), 1, TimeUnit.SECONDS);
    }

    /**
     * 禁锢类buff
     */
    public void moveBuff(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        moveLimited = true;
        int lastTime = buff.getMaxLastTime();
        String res = "\n" + unit.getName() + " 中了 " + buff.getName() + " 持续: " + buff.getMaxLastTime() + " 无法行动";
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), res);
        gameMapActor.schedule(gameMapActor1 -> {
            moveBuffLift(buff.getName(), gameMapActor, writeMessage2Client);
        }, lastTime, TimeUnit.SECONDS);
    }

    public void moveBuffLift(String buffName, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        moveLimited = false;
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + unit.getName() + " 解除" + buffName);
    }

    /**
     * 晕眩类buff(如冰冻、晕眩等)
     */
    public void actionBuff(Buff buff, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        actionLimited = true;
        int lastTime = buff.getMaxLastTime();
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n"+ unit.getName() + " 中了: " + buff.getName()
                + " 持续: " + buff.getMaxLastTime() + " 无法行动");
        gameMapActor.schedule(gameMapActor1 -> {
            actionBuffLift(buff.getName(), gameMapActor, writeMessage2Client);
        }, lastTime, TimeUnit.SECONDS);
    }

    public void actionBuffLift(String buffName, GameMapActor gameMapActor, WriteMessage2Client writeMessage2Client) {
        actionLimited = false;
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + unit.getName() + " 解除: " + buffName);
    }

    /**
     * 是否能攻击
     */
    public boolean canAction() {
        return actionLimited;
    }

    /**
     * 能否移动
     */
    public boolean canMove() {
        return !actionLimited && !moveLimited;
    }

    public void setBuffMap(Map<Integer, Buff> buffMap) {
        this.buffMap = buffMap;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
