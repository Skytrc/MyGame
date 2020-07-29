package com.fung.server.gameserver.content.config.manager;

import com.fung.server.gameserver.content.config.buff.Buff;
import com.fung.server.gameserver.content.config.readconfig.ReadBuff;
import com.fung.server.gameserver.content.domain.buff.BuffSpecies;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/7/23 10:54
 */
@Component
public class BuffManager {

    private Map<Integer, Buff> buffMap;

    @Autowired
    private ReadBuff readBuff;

    public void buffInit() throws IOException, InvalidFormatException {
        readBuff.init();
        buffMap = readBuff.getModelMap();
    }

    public Map<Integer, Buff> getBuffMap() {
        return buffMap;
    }

    /**
     * 根据模板创建新的buff
     */
    public Buff createNewBuff(int buffId) {
        Buff templateBuff = buffMap.get(buffId);
        Buff buff = new Buff();
        buff.setName(templateBuff.getName());
        buff.setMaxLastTime(templateBuff.getMaxLastTime());
        buff.setDescription(templateBuff.getDescription());
        buff.setDamagePerSecond(templateBuff.getDamagePerSecond());
        buff.setMaxLayer(templateBuff.getMaxLayer());
        buff.setCanAction(templateBuff.getCanAction());
        buff.setCanMove(templateBuff.getCanMove());
        buff.setShield(templateBuff.getShield());
        buff.setId(templateBuff.getId());
        return buff;
    }

}
