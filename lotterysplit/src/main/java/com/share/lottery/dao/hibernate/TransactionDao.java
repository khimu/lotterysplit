package com.share.lottery.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.dao.ITransactionDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.Payment;
import com.share.lottery.model.Transaction;

@Repository("transactionDao")
public class TransactionDao extends GenericDaoHibernate<Transaction, Long> implements ITransactionDao{

	public TransactionDao() {
		super(Transaction.class);
	}
	
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    
	public Integer getAllMyTransactionsCount(Long userId){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from Transaction where userId = :userId");
    	query.setParameter("userId", userId);
    	return ((Long) query.uniqueResult()).intValue();	
    }    

    public List<Transaction> findTransactionsByUserId(Long userId, int start){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Transaction.class).add(Restrictions.eq("userId", userId));

        criteria.setFetchSize(50);
        criteria.setFirstResult(start);
        criteria.setMaxResults(start+50);
	    
	    return  (List<Transaction>) criteria.list();
    }
    
	   @Override
	    @ReadThroughSingleCache(namespace = "Transaction", expiration = 3600)
	    public Transaction get(@ParameterValueKeyProvider Long id) {
	    	return super.get(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Transaction")
	    public Transaction save(Transaction b) {
	        return super.save(b);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Transaction")
	    public void remove(Long id) {
	        super.remove(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Transaction")    
	    public void remove(Transaction obj) {
	    	super.remove(obj);
	    } 
}
