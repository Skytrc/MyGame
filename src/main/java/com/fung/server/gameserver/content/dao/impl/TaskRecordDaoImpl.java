package com.fung.server.gameserver.content.dao.impl;

import com.fung.server.gameserver.content.dao.TaskRecordDao;
import com.fung.server.gameserver.content.entity.TaskRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/8/5 12:43
 */
@Transactional
@Component
public class TaskRecordDaoImpl extends HibernateDaoSupport implements TaskRecordDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void insertOrUpdate(TaskRecord taskRecord) {
        this.getHibernateTemplate().saveOrUpdate(taskRecord);
    }

    @Override
    public List<TaskRecord> findTaskRecordByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            TypedQuery<TaskRecord> query = session.createQuery("FROM task_record WHERE player_id=:player_id", TaskRecord.class)
                    .setParameter("player_id", playerId);
            return query.getResultList();
        } catch (NoResultException ignored) {
            return null;
        }

    }
}
