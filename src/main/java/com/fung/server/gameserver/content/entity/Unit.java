package com.fung.server.gameserver.content.entity;

/**
 * @author skytrc@163.com
 * @date 2020/7/22 20:05
 */
public interface Unit {

    String getName();

    void setHealthPoint(int healthPoint);

    int getHealthPoint();

    int getInMapId();

    int getInMapX();

    int getInMapY();

    int getTempX();

    int getTempY();

    int getAttackDistance();
}
