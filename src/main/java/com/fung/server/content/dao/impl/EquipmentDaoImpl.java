package com.fung.server.content.dao.impl;

import com.fung.server.content.dao.EquipmentDao;
import com.fung.server.content.entity.Equipment;
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
 * @date 2020/6/1 19:29
 */
@Transactional
@Component
public class EquipmentDaoImpl extends HibernateDaoSupport implements EquipmentDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void insertEquipment(Equipment equipment) {
        this.getHibernateTemplate().save(equipment);
    }

    @Override
    public void updateEquipment(Equipment equipment) {
        this.getHibernateTemplate().update(equipment);
    }

    @Override
    public List<Equipment> findEquipmentsByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            return session.createNativeQuery(
                    "SELECT * " +
                            "FROM equipment " +
                            "WHERE player_id=" + playerId + ";", Equipment.class
            ).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
