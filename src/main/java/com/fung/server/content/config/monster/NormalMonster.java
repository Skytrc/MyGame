package com.fung.server.content.config.monster;

import com.fung.server.excel2class.Model;

/**
 * 普通怪兽
 * @author skytrc@163.com
 * @date 2020/5/28 17:18
 */
public class NormalMonster extends BaseMonster implements Model {

    public NormalMonster() {
        this.setFriendly(false);
    }

}
