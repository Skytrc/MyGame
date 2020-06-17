package com.fung.server.content.config.manager;

import com.fung.server.content.config.npc.NpcShop;
import com.fung.server.content.config.readconfig.ReadNpcShop;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/17 11:24
 */
@Component
public class NpcShopManager {

    @Autowired
    ReadNpcShop readNpcShop;

    private Map<Integer, List<Integer>> npcShopMap;

    public void npcShopInit() throws IOException, InvalidFormatException {
        readNpcShop.init();

        HashMap<Integer, NpcShop> modelMap = readNpcShop.getModelMap();
        npcShopMap = new HashMap<>();
        modelMap.forEach((key, value) -> {
            npcShopMap.put(value.getNpcId(), string2List(value.getGoodsId()));
        });

    }

    public List<Integer> string2List(String s) {
        String[] strings = s.split(",");
        List<Integer> goodsId = new ArrayList<>();
        for (String string : strings) {
            goodsId.add(Integer.valueOf(string));
        }
        return goodsId;
    }

    public Map<Integer, List<Integer>> getNpcShopMap() {
        return npcShopMap;
    }

    public void setNpcShopMap(Map<Integer, List<Integer>> npcShopMap) {
        this.npcShopMap = npcShopMap;
    }
}
