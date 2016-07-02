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
import com.share.lottery.Constants;
import com.share.lottery.dao.ILotteryBuyerDao;
import com.share.lottery.model.Balance;
import com.share.lottery.model.LotteryBuyer;
import com.share.lottery.model.Payment;
import com.share.lottery.service.ISpyMemcachedHandler;

@Repository("lotteryBuyerDao")
public class LotteryBuyerDao extends GenericDaoHibernate<LotteryBuyer, Long> implements ILotteryBuyerDao {

	//@Autowired
	//private ISpyMemcachedHandler memcachedHandler;
	
    public LotteryBuyerDao() {
        super(LotteryBuyer.class);
    }
    
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    
	public Integer getAllSoldCount(Long userId){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from LotteryBuyer where userId = :userId");
    	query.setParameter("userId", userId);
    	return ((Long) query.uniqueResult()).intValue();	
    }    
	
	public Integer getAllBoughtCount(Long buyerUserId){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from LotteryBuyer where buyerUserId = :userId");
    	query.setParameter("userId", buyerUserId);
    	return ((Long) query.uniqueResult()).intValue();	
    }    	

	public List<LotteryBuyer> findAllSold(Long userId, int start){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LotteryBuyer.class).add(Restrictions.eq("userId", userId));

        criteria.setFetchSize(50);
        criteria.setFirstResult(start);
        criteria.setMaxResults(start+50);
	    
	    return  (List<LotteryBuyer>) criteria.list();
    }    
	
	public List<LotteryBuyer> findAllBought(Long buyerUserId, int start){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LotteryBuyer.class).add(Restrictions.eq("buyerUserId", buyerUserId));

        criteria.setFetchSize(50);
        criteria.setFirstResult(start);
        criteria.setMaxResults(start+50);
	    
	    return  (List<LotteryBuyer>) criteria.list();
    }    		
    
	public LotteryBuyer findAlreadyBought(Long buyerUserId, Long lotteryId){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LotteryBuyer.class).add(Restrictions.eq("buyerUserId", buyerUserId));
        criteria.add(Restrictions.eq("lotteryTicketId", lotteryId));
	    
	    List<LotteryBuyer> list = criteria.list();
	    if(list != null && list.isEmpty() == false){
	    	return list.get(0);
	    }else{
	    	return null;
	    }
    } 
	
    @Override
    //@ReadThroughSingleCache(namespace = "LotteryBuyer", expiration = 3600)
    public LotteryBuyer get(@ParameterValueKeyProvider Long id) {
    	return super.get(id);
    }
    
    @Override
    //@InvalidateSingleCache(namespace = "LotteryBuyer")
    public LotteryBuyer save(LotteryBuyer b) {
    	//memcachedHandler.addAnItem(Constants.ALL_BUYS, b);
        return super.save(b);
    }
    
    @Override
    //@InvalidateSingleCache(namespace = "LotteryBuyer")
    public void remove(Long id) {
    	//memcachedHandler.deleteCache(Constants.ALL_BUYS);
        super.remove(id);
    }
    
    @Override
    //@InvalidateSingleCache(namespace = "LotteryBuyer")    
    public void remove(LotteryBuyer obj) {
    	//memcachedHandler.deleteCache(Constants.ALL_BUYS);
    	super.remove(obj);
    } 
    
}
