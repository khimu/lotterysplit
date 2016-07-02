package com.share.lottery.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.dao.IBalanceDao;
import com.share.lottery.model.Balance;

@Repository("balanceDao")
public class BalanceDao extends GenericDaoHibernate<Balance, Long> implements IBalanceDao {

    public BalanceDao() {
        super(Balance.class);
    }
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    
    @Override
    @ReadThroughSingleCache(namespace = "Balance", expiration = 3600)
    public Balance get(@ParameterValueKeyProvider Long id) {
    	return super.get(id);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "Balance")
    public Balance save(Balance b) {
        return super.save(b);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "Balance")
    public void remove(Long id) {
        super.remove(id);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "Balance")    
    public void remove(Balance obj) {
    	super.remove(obj);
    } 
    
    @Override
    public Balance findBalanceByUserId(Long userId) throws UsernameNotFoundException {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List balance = session.createCriteria(Balance.class).add(Restrictions.eq("userId", userId)).list();
        if (balance == null || balance.isEmpty()) {
            throw new UsernameNotFoundException("user '" + userId + "' not found...");
        } else {
            return (Balance) balance.get(0);
        }
    }
    
}
