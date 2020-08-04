package com.fung.server.gameserver.content.dao.impl;

import com.fung.server.gameserver.content.dao.GuildDao;
import com.fung.server.gameserver.content.entity.guild.Guild;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 10:41
 */
@Transactional
@Component
public class GuildDaoImpl extends HibernateDaoSupport implements GuildDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void insertOrUpdateGuild(Guild guild) {
        this.getHibernateTemplate().saveOrUpdate(guild);
    }

    @Override
    public void deleteGuild(Guild guild) {
        this.getHibernateTemplate().delete(guild);
    }

    @Override
    public Guild selectGuildById(String guildId) {
        return this.getHibernateTemplate().get(Guild.class, guildId);
    }

    @Override
    public List<Guild> selectAllGuild() {
        Session session = this.getSessionFactory().openSession();
        TypedQuery<Guild> query = session.createQuery("FROM guild");
        return query.getResultList();
    }
}
