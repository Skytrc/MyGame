package com.fung.server.chatserver.dao.impl;

import com.fung.server.chatserver.dao.ChatPlayerDao;
import com.fung.server.chatserver.entity.ChatPlayer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

/**
 * @author skytrc@163.com
 * @date 2020/6/24 12:09
 */
@Transactional
@Component
public class ChatPlayerDaoImpl extends HibernateDaoSupport implements ChatPlayerDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public ChatPlayer getPlayerByPlayerName(String playerName) {
        Session session = this.getSessionFactory().openSession();
        try {
            return (ChatPlayer) session.createNativeQuery("SELECT *" +
                    " FROM player" +
                    " WHERE player_name= '" + playerName + "'").addEntity(ChatPlayer.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
