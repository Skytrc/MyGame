package com.fung.server.content.dao;

import com.fung.server.content.entity.Skill;

import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 12:53
 */
public interface SkillDao {

    /**
     * 插入新增技能
     * @param skill 技能
     */
    void insertSkill(Skill skill);

    /**
     * 更新技能
     * @param skill 技能
     */
    void updateSkill(Skill skill);

    /**
     * 根据玩家id 获得玩家的所有技能
     * @param playerId 玩家id
     * @return 玩家所有技能
     */
    List<Skill> findSkillsByPlayerId(String playerId);
}
