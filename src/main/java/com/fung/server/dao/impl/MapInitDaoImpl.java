package com.fung.server.dao.impl;

import com.fung.server.content.entity.GameMap;
import com.fung.server.dao.MapInitDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 11:38
 */
@Transactional
@Component
public class MapInitDaoImpl extends HibernateDaoSupport implements MapInitDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void saveMap(GameMap gameMap) {
        this.getHibernateTemplate().save(gameMap);
    }
}
