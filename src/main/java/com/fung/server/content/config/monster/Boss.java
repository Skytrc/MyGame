package com.fung.server.content.config.monster;

import com.fung.server.excel2class.Model;

import java.util.Map;

/**
 * BOSS
 * @author skytrc@163.com
 * @date 2020/6/15 11:52
 */
public class Boss extends BaseMonster implements Model {

    /**
     * 仇恨榜
     */
    private Map<String, Integer> hateMap;

    public Boss() {
        this.setFriendly(false);
    }

    public Map<String, Integer> getHateMap() {
        return hateMap;
    }

    public void setHateMap(Map<String, Integer> hateMap) {
        this.hateMap = hateMap;
    }
}
