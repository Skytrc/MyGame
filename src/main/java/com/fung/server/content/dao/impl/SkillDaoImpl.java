package com.fung.server.content.dao.impl;

import com.fung.server.content.dao.SkillDao;
import com.fung.server.content.entity.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 12:58
 */
@Transactional
@Component
public class SkillDaoImpl extends HibernateDaoSupport implements SkillDao{

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void insertSkill(Skill skill) {
        this.getHibernateTemplate().save(skill);
    }

    @Override
    public void updateSkill(Skill skill) {
        this.getHibernateTemplate().update(skill);
    }

    @Override
    public List<Skill> findSkillsByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            return session.createNativeQuery(
                    "SELECT * " +
                            "FROM skill " +
                            "WHERE player_id='" + playerId + "';"
                    , Skill.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
