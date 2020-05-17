package com.fung.server.dao.impl;

import com.fung.server.content.entity.Player;
import com.fung.server.dao.LocationDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author skytrc@163.com
 * @date 2020/5/12 16:55
 */
@Transactional
@Component
public class LocationDaoImpl extends HibernateDaoSupport implements LocationDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void updatePlayerLocation(Player player) {
        this.getHibernateTemplate().update(player);
    }
}
