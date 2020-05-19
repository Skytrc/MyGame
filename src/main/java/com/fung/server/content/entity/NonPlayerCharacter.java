package com.fung.server.content.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author skytrc@163.com
 * @date 2020/5/19 15:07
 */
@MappedSuperclass
public class NonPlayerCharacter extends BaseElement{

    @Column(name = "max_hit_point")
    private int maxHitPoint;

    @Column(name = "hit_point")
    private int hitPoint;

    @Column(name = "magic_point")
    private int magicPoint;

    @Column(name = "attackPower")
    private int attackPower;

    @Column
    private int defense;

    private boolean friendly;


}
