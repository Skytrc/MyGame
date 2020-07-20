package com.fung.server.gameserver.content.dao;

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
            TypedQuery<Email> query = session.createQuery("FROM Email WHERE email_id= :email_id", Email.class)
                    .setParameter("email_id", emailId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void createEmailStatus(EmailStatus emailStatus) {
        this.getHibernateTemplate().save(emailStatus);
    }

    @Override
    public void updateEmailStatus(EmailStatus emailStatus) {
        this.getHibernateTemplate().update(emailStatus);
    }

    @Override
    public void deleteEmailStatus(String emailId, String playerId) {
        Session session = this.getSessionFactory().openSession();
        try {

        } catch (NoResultException e) {
            return;
        }
    }

    @Override
    public List<Email> getAllEmailByPlayerId(String playerId) {
//        Session session = this.getSessionFactory().openSession();
//        try {
//            TypedQuery<Email> query = session.createQuery("FROM Email WHERE sender= :email_id", Email.class)
//                    .setParameter("email_id", emailId);
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
        return null;
    }

    @Override
    public void deleteEmailBy(Email email) {
        this.getHibernateTemplate().delete(email);
    }
}
