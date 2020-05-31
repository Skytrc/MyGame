package com.fung.server.content.domain;

import com.fung.server.content.entity.base.BaseElement;
import com.fung.server.excel2class.Model;

/**
 * 怪兽
 * @author skytrc@163.com
 * @date 2020/5/28 17:18
 */
public class Monster extends BaseElement implements Model {

    /**
     * 每个怪兽实体唯一ID
     */
    private String uuid;

    /**
     * 怪物等级
     */
    private int level;

    /**
     * 最大生命值
     */
    private int maxHealthPoint;

    /**
     * 目前生命值
     */
    private int healthPoint;

    /**
     * 攻击力
     */
    private int attackPower;

    /**
     * 魔法力
     */
    private int magicPower;

    /**
     * 防御
     */
    private int defend;

}
