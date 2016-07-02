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
import com.share.lottery.dao.IPaymentDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.Payment;

@Repository("paymentDao")
public class PaymentDao extends GenericDaoHibernate<Payment, Long> implements IPaymentDao{

	public PaymentDao() {
		super(Payment.class);
	}
	
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    
	public Integer getAllMyPaymentsCount(Long userId){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from Payment where userId = :userId");
    	query.setParameter("userId", userId);
    	return ((Long) query.uniqueResult()).intValue();	
    }    
	
    public List<Payment> findPaymentsByUserId(Long userId, int start){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Payment.class).add(Restrictions.eq("userId", userId));

        criteria.setFetchSize(50);
        criteria.setFirstResult(start);
        criteria.setMaxResults(start+50);
	    
	    return  (List<Payment>) criteria.list();
    }
    
	   @Override
	    @ReadThroughSingleCache(namespace = "Payment", expiration = 3600)
	    public Payment get(@ParameterValueKeyProvider Long id) {
	    	return super.get(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Payment")
	    public Payment save(Payment b) {
	        return super.save(b);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Payment")
	    public void remove(Long id) {
	        super.remove(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Payment")    
	    public void remove(Payment obj) {
	    	super.remove(obj);
	    } 

}
