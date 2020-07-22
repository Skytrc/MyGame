package com.fung.server.gameserver.content.domain.buff;

import com.fung.server.gameserver.channelstore.WriteMessage2Client;
import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.content.domain.mapactor.GameMapActor;
import com.fung.server.gameserver.content.entity.Unit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * buff管理
 * @author skytrc@163.com
 * @date 2020/7/22 10:51
 */
public class BuffManager {

    /**
     * 用于记录生物身上的buff
     */
    private Map<Integer, Buff> buffMap;

    /**
     * 双向绑定
     */
    private Unit unit;

    /**
     * 地图线程
     */
    private GameMapActor gameMapActor;

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

    @Autowired
    private WriteMessage2Client writeMessage2Client;

    /**
     * 添加buff
     */
    public void putOnBuff(Buff buff, String channelId) {
        trigger(buff, channelId);
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

    public void trigger(Buff buff, String channelId) {
        switch (buff.getBuffSpecies()) {
            case MoveLimit:
                moveBuff(buff, channelId);
                break;
            case Dot:
                newDotCalculate(buff);
                break;
            case Shield:
                shieldJudgement(buff);
                break;
            case ActionLimit:
                actionBuff(buff);
                break;
            default:
        }
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
    public void shieldJudgement(Buff buff) {
        if (shield < buff.getShield()) {
            shield = buff.getShield();
        }
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n获得新护盾,数值为: " + shield);
    }

    /**
     * 新的dot处理
     */
    public void newDotCalculate(Buff buff) {
        int buffId = buff.getId();
        // 判断是否有同类buff
        if (buffMap.containsKey(buffId)) {
            Buff buff1 = buffMap.get(buffId);
            // 重置持续时间
            buff1.setLastTime(0);
            // 检查层数
            if (buff1.getLayer() + 1 < buff1.getMaxLastTime()) {
                buff1.setLayer(buff1.getLayer() + 1);
            }
            buff = buff1;
        } else {
            buffMap.put(buffId, buff);
        }
        Buff finalBuff = buff;
        gameMapActor.addMessage(gameMapActor1 -> {
            dotCalculate(finalBuff);
        });
    }

    /**
     * 持续状态计算
     */
    public void dotCalculate(Buff buff) {
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
        String res = unit.getName() + " 身上buff: " + buff.getName()
                + " 层数: " + buff.getLayer() + (minusHp > 0 ? " 扣除: " : " 增加: ") + minusHp
                + " 剩余: " + remainingTime + "秒";
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), res);
        // 加入地图线程队列
        gameMapActor.schedule(gameMapActor1 -> dotCalculate(buff), 1, TimeUnit.SECONDS);
    }

    /**
     * 禁锢类buff
     */
    public void moveBuff(Buff buff, String channelId) {
        moveLimited = true;
        int lastTime = buff.getLastTime();
        String res = "\n" + unit.getName() + " 移除 " + buff.getName();
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), res);
        gameMapActor.schedule(gameMapActor1 -> {
            moveBuffLift(buff.getName());
        }, lastTime, TimeUnit.SECONDS);
    }

    public void moveBuffLift(String buffName) {
        moveLimited = false;
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + unit.getName() + " 解除" + buffName);
    }

    /**
     * 晕眩类buff(如冰冻、晕眩等)
     */
    public void actionBuff(Buff buff) {
        actionLimited = true;
        int lastTime = buff.getLastTime();
        gameMapActor.schedule(gameMapActor1 -> {
            actionBuffLift(buff.getName());
        }, lastTime, TimeUnit.SECONDS);
    }

    public void actionBuffLift(String buffName) {
        actionLimited = false;
        writeMessage2Client.writeMessage2MapPlayer(gameMapActor.getGameMap(), "\n" + unit.getName() + " 解除: " + buffName);
    }

    /**
     * 是否能攻击
     */
    public boolean attackCalculate() {
        return !actionLimited;
    }

    /**
     * 能否移动
     */
    public boolean moveCalculate() {
        return !moveLimited;
    }

    public void setBuffMap(Map<Integer, Buff> buffMap) {
        this.buffMap = buffMap;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setGameMapActor(GameMapActor gameMapActor) {
        this.gameMapActor = gameMapActor;
    }
}
