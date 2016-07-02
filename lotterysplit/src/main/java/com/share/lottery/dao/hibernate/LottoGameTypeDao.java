package com.share.lottery.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.share.lottery.dao.ILottoGameTypeDao;
import com.share.lottery.model.LottoGameType;

@Repository("lottoGameTypeDao")
public class LottoGameTypeDao extends GenericDaoHibernate<LottoGameType, Long> implements ILottoGameTypeDao {
	
    public LottoGameTypeDao() {
        super(LottoGameType.class);
    }
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    

}
