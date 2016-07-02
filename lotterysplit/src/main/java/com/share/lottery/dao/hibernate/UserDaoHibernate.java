package com.share.lottery.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.Constants;
import com.share.lottery.dao.UserDao;
import com.share.lottery.model.User;
import com.share.lottery.service.ISpyMemcachedHandler;

/**
 * This class interacts with Hibernate session to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with
 *   the new BaseDaoHibernate implementation that uses generics.
 *   Modified by jgarcia (updated to hibernate 4)
*/
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {


	//@Autowired
	//private ISpyMemcachedHandler memcachedHandler;
	
	
    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super(User.class);
    }
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query qry = session.createQuery("from User u order by upper(u.username)");
        return qry.list();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) {
    	//memcachedHandler.addAnItem(Constants.RANDOM_PLAYERS, user);
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + user.getId());
        }
        session.saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        session.flush();
        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     *
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    @InvalidateSingleCache(namespace = "User")
    public User save(User b) {
    	return this.saveUser(b);
    }

    /**
     * {@inheritDoc}
    */
    @ReadThroughSingleCache(namespace = "User", expiration = 3600)
    public UserDetails loadUserByUsername(@ParameterValueKeyProvider String username) throws UsernameNotFoundException {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List users = session.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
    */
    public String getUserPassword(Long userId) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Criteria criteria = session.createCriteria(User.class);
    	criteria.add(Restrictions.gt("id", userId));
    	criteria.setProjection(Projections.property("password"));
    	List result = criteria.list();
    	if(result != null && result.isEmpty() == false){
    		return (String)result.get(0);
    	}
    	return null;
    }
    
    public void updateReferralCount(String referralCode) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update User m set m.referralCount = m.referralCount + 1 where m.referralCode = :referralCode";
		Query query = session.createQuery(sql);
		query.setParameter("referralCode", referralCode);
		int success = query.executeUpdate();
    }
    
    public User findByReferralCode(String referralCode){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List users = session.createCriteria(User.class).add(Restrictions.eq("referralCode", referralCode)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + referralCode + "' not found...");
        } else {
            return (User) users.get(0);
        }
    }
    
    public List<User> getPalyers(){
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "from User";
		Query query = session.createQuery(sql);
		query.setFetchSize(10);
		return (List<User>) query.list();
    }
    
	public List<Long> findAllUserForEmails(String emails){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Query query = session.createQuery("select id from User where email in (:emails)");
    	query.setParameter("emails", emails);
    	return (List<Long>) query.list();
	}   
    
	   @Override
	   // @ReadThroughSingleCache(namespace = "User", expiration = 3600)
	    public User get(@ParameterValueKeyProvider Long id) {
	    	return super.get(id);
	    }

	    @Override
	    //@InvalidateSingleCache(namespace = "User")
	    public void remove(Long id) {
	    	//memcachedHandler.deleteCache(Constants.RANDOM_PLAYERS);
	        super.remove(id);
	    }
	    
	    @Override
	    //@InvalidateSingleCache(namespace = "User")    
	    public void remove(User obj) {
	    	//memcachedHandler.deleteCache(Constants.RANDOM_PLAYERS);
	    	super.remove(obj);
	    } 
}
