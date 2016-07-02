package com.share.lottery.dao.hibernate;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.dao.RoleDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.Role;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;


/**
 * This class interacts with hibernate session to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @author jgarcia (updated to hibernate 4)
 */
@Repository
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super(Role.class);
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
    public Role getRoleByName(String rolename) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        List roles = session.createCriteria(Role.class).add(Restrictions.eq("name", rolename)).list();
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        Session session = getSessionFactory().getCurrentSession();
        session.delete(role);
    }
    
	   @Override
	    @ReadThroughSingleCache(namespace = "Role", expiration = 3600)
	    public Role get(@ParameterValueKeyProvider Long id) {
	    	return super.get(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Role")
	    public Role save(Role b) {
	        return super.save(b);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Role")
	    public void remove(Long id) {
	        super.remove(id);
	    }
	    
	    @Override
	    @InvalidateSingleCache(namespace = "Role")    
	    public void remove(Role obj) {
	    	super.remove(obj);
	    } 
}
