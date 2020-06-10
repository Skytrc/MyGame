package com.fung.server.content.config.good.equipment;

import com.fung.server.content.entity.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * 武器类型
 * @author skytrc@163.com
 * @date 2020/5/28 16:49
 * TODO 装备类型
 */
public enum EquipmentType {

    /**
     * 头部装备
     */
    HAT(0, "hat"),

    /**
     * 武器
     */
    WEAPON(1, "weapon"),

    /**
     * 上衣
     */
    COAT(2, "coat"),

    /**
     * 裤子
     */
    PANTS(3, "pants"),

    /**
     * 鞋子
     */
    SHOE(4, "shoe")
    ;

    /**
     * 装备所在位置
     */
    private final int position;

    /**
     * 装备的类型
     */
    private final String type;


    public static int[] ATTACK_TYPE = {1};

    EquipmentType(int position, String type) {

        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public static EquipmentType getEquipmentTypeByPartName(String name) {
        switch (name) {
            case "hat": return HAT;
            case "weapon": return WEAPON;
            case "coat": return COAT;
            case "pants": return PANTS;
            case "shoe": return SHOE;
            default: return null;
        }
    }

    public static boolean isDefenseType(Equipment equipment) {
        EquipmentType type = equipment.getType();
        switch (type) {
            case HAT :
            case COAT :
            case SHOE :
            case PANTS :
                return true;
            default: return false;
        }
    }
}
