package com.fung.server.dao.impl;

import com.fung.server.content.entity.Player;
import com.fung.server.dao.UserDao;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * @author skytrc@163.com
 * @date 2020/5/11 12:05
 */
@Transactional
@Component
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void playerRegister(Player player) {
        this.getHibernateTemplate().save(player);
    }

    @Override
    public boolean findPlayerInfo(Player player) {
        return this.getHibernateTemplate().findByExample(player).size() > 0;
    }

    @Override
    public Player getPlayerByPlayerNamePassword(String playerName, String password) {
        Session session = this.getSessionFactory().openSession();
        try {
            return session.createNativeQuery(
                    "SELECT * " +
                            "FROM player " +
                            "WHERE player_name='" + playerName + "' AND password='" + password + "'", Player.class).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Player getPlayerAllInfo(Player player) {
        List<Player> players = this.getHibernateTemplate().findByExample(player);
        return players.remove(0);
    }

}
