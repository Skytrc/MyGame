package com.fung.server.gameserver.content.dao.impl;

import com.fung.server.gameserver.content.dao.GoodDao;
import com.fung.server.gameserver.content.entity.Equipment;
import com.fung.server.gameserver.content.entity.Good;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/6/4 10:34
 */
@Transactional
@Component
public class GoodDaoImpl extends HibernateDaoSupport implements GoodDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public List<Good> getGoodByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            TypedQuery<Good> query = session.createQuery("FROM good WHERE player_id= :player_id AND position!=-1", Good.class)
                    .setParameter("player_id", playerId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Equipment> findBackpackEquipment(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            return session.createNativeQuery("SELECT * FROM good WHERE player_id= '" + playerId +
                    "' AND DTYPE='equipment' AND position > -1 ", Equipment.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Good findGoodByGoodUuid(String goodUuid) {
        try {
            return this.getHibernateTemplate().get(Good.class, goodUuid);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void insertOrUpdateGood(Good good) {
        this.getHibernateTemplate().saveOrUpdate(good);
    }

    @Override
    public void deleteGood(Good good) {
        this.getHibernateTemplate().delete(good);
    }
}
