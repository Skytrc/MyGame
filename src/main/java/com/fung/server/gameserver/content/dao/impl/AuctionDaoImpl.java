package com.fung.server.gameserver.content.dao.impl;

import com.fung.server.gameserver.content.dao.AuctionDao;
import com.fung.server.gameserver.content.entity.AuctionItemRecord;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author skytrc@163.com
 * @date 2020/8/3 21:14
 */
@Transactional
@Component
public class AuctionDaoImpl extends HibernateDaoSupport implements AuctionDao {

    @Autowired
    public void setSuperSessionFactory(SessionFactory superSessionFactory) {
        super.setSessionFactory(superSessionFactory);
    }

    @Override
    public void insertOrUpdateAuctionRecord(AuctionItemRecord auctionItemRecord) {
        this.getHibernateTemplate().saveOrUpdate(auctionItemRecord);
    }

    @Override
    public AuctionItemRecord getRecord(String recordId) {
        return this.getHibernateTemplate().get(AuctionItemRecord.class, recordId);
    }
}
