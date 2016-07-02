package com.share.lottery.dao.hibernate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.dao.ILotteryTicketDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.service.ISpyMemcachedHandler;

@Repository("lotteryTicketDao")
public class LotteryTicketDao extends GenericDaoHibernate<LotteryTicket, Long> implements ILotteryTicketDao {


	//@Autowired
	//private ISpyMemcachedHandler memcachedHandler;
	
	
    public LotteryTicketDao() {
        super(LotteryTicket.class);
    }
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 

    public List<LotteryTicket> getAll(int start){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM lottery_ticket order by (split_number - buyer_count) desc, ticket_date desc";
		Query query = session.createSQLQuery(sql).addEntity(LotteryTicket.class);
	    query.setFetchSize(50);
	    query.setFirstResult(start);
	    query.setMaxResults(start+50);
	    
	    return  (List<LotteryTicket>) query.list();
    }
    
    public Integer getAllCount(){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	return ((Long) session.createQuery("select count(*) from LotteryTicket").uniqueResult()).intValue();
    }
    
	public Integer getAllOthersCount(Long userId){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		Date today = new Date();
		String date = format.format(today);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from LotteryTicket where userId not in (:userId)");
    	query.setParameter("userId", userId);
    	//query.setParameter("today", date);
    	return ((Long) query.uniqueResult()).intValue();
	}
	

	@Override
	public List<LotteryTicket> getAllTicketsInList(int start, List<Long> lotteryIds) {
		
		Date today = new Date();
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(LotteryTicket.class).add(Restrictions.in("id", lotteryIds));
    	criteria.add(Restrictions.ge("ticketDate",today));
        criteria.addOrder(Order.desc("available"));
        criteria.addOrder(Order.desc("ticketDate"));
        criteria.setFetchSize(50);
        criteria.setFirstResult(start);
        criteria.setMaxResults(start+50);
	    
	    return  (List<LotteryTicket>) criteria.list();
	}
	
	@Override
	public LotteryTicket getRandomFreeLottery(){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(LotteryTicket.class).add(Restrictions.eq("cost", new BigDecimal(0.00)));
		Date today = new Date();
		criteria.add(Restrictions.ge("ticketDate", today));
		return (LotteryTicket)criteria.uniqueResult();
	}
	

	@Override
	public List<LotteryTicket> getAllOthers(int start, Long userId) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		Date today = new Date();
		String date = format.format(today);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "SELECT * FROM lottery_ticket where user_id not in (:userId) order by (split_number - buyer_count) desc, ticket_date desc";
		Query query = session.createSQLQuery(sql).addEntity(LotteryTicket.class);
		query.setParameter("userId", userId);
		//query.setParameter("today", date);
	    query.setFetchSize(50);
	    query.setFirstResult(start);
	    query.setMaxResults(start+50);
	    
	    return  (List<LotteryTicket>) query.list();
	}
	
	public Integer getAllOrderCount(){
		Date today = new Date();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from LotteryTicket where alreadyPurchased = 0");
    	return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public List<LotteryTicket> getAllOrders(int start) {
		Date today = new Date();

    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LotteryTicket.class).add(Restrictions.eq("alreadyPurchased", false));
		criteria.setFetchSize(50);
		criteria.setFirstResult(start);
		criteria.setMaxResults(start+50);
	     
	    return  criteria.list();
	}	
	
	public Integer getAllMyListCount(Long userId){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select count(*) from LotteryTicket where userId = :userId");
    	query.setParameter("userId", userId);
    	return ((Long) query.uniqueResult()).intValue();	
    }


	@Override
	public List<LotteryTicket> getAllMyList(int start, Long userId) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LotteryTicket.class).add(Restrictions.eq("userId", userId));
        criteria.addOrder(Order.desc("available"));
        criteria.addOrder(Order.desc("ticketDate"));
		criteria.setFetchSize(50);
		criteria.setFirstResult(start);
		criteria.setMaxResults(start+50);
	    
	    return  (List<LotteryTicket>) criteria.list();
	}
	
	   @Override
	    @ReadThroughSingleCache(namespace = "LotteryTicket", expiration = 3600)
	    public LotteryTicket get(@ParameterValueKeyProvider Long id) {
	    	return super.get(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "LotteryTicket")
	    public LotteryTicket save(LotteryTicket b) {
	    	//memcachedHandler.addAnItem(Constants.ALL_TOP_LISTERS, b);
	        return super.save(b);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "LotteryTicket")
	    public void remove(Long id) {
	    	//memcachedHandler.deleteCache(Constants.ALL_TOP_LISTERS);
	        super.remove(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "LotteryTicket")    
	    public void remove(LotteryTicket obj) {
	    	//memcachedHandler.deleteCache(Constants.ALL_TOP_LISTERS);
	    	super.remove(obj);
	    } 
}
