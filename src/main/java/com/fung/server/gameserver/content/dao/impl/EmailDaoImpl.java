package com.fung.server.gameserver.content.dao.impl;

import com.fung.server.gameserver.content.dao.EmailDao;
import com.fung.server.gameserver.content.entity.Email;
import com.fung.server.gameserver.content.entity.EmailStatus;
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
 * @date 2020/7/16 17:02
 */
@Transactional
@Component
public class EmailDaoImpl extends HibernateDaoSupport implements EmailDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void createNewEmail(Email email) {
        this.getHibernateTemplate().save(email);
    }

    @Override
    public void updateEmail(Email email) {
        this.getHibernateTemplate().update(email);
    }

    @Override
    public Email getEmailById(String emailId) {
        Session session = this.getSessionFactory().openSession();
        try {
            TypedQuery<Email> query = session.createQuery("FROM Email WHERE uuid = :email_id ", Email.class)
                    .setParameter("email_id", emailId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Email> getAllRecipientEmailByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            TypedQuery<Email> query = session.createQuery("FROM Email WHERE recipient_id= :recipient_id " +
                    "AND recipient_delete=0 AND send_time!=0", Email.class)
                    .setParameter("recipient_id", playerId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Email> getAllSendEmailByPlayerId(String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {
            TypedQuery<Email> query = session.createQuery("FROM Email WHERE sender_id= :sender_id ", Email.class)
                    .setParameter("sender_id", playerId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteEmailBy(Email email) {
        this.getHibernateTemplate().delete(email);
    }
}
