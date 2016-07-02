package com.share.lottery.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.share.lottery.dao.ILottoGameDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.LottoGame;

@Repository("lottoGameDao")
public class LottoGameDao extends GenericDaoHibernate<LottoGame, Long> implements ILottoGameDao {
	
    public LottoGameDao() {
        super(LottoGame.class);
    }
    
    @Autowired
    @Required
    public void setSessionFactory(@Qualifier("lotterySplitSessionFactory") final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    } 
    
    public void updateDrawing(String name, String winningAmount, String numbers, String prevDrawingDate, String nextDrawingDate) {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update LottoGame m set m.numbers = :numbers, m.prevDrawingDate = :prevDrawingDate, m.nextDrawingDate = :nextDrawingDate, m.winningAmount = :winningAmount where lower(m.gameName) = :gameName";
		
		Query query = session.createQuery(sql);
		query.setParameter("nextDrawingDate", nextDrawingDate);
		query.setParameter("prevDrawingDate", prevDrawingDate);
		query.setParameter("winningAmount", winningAmount);
		query.setParameter("numbers", numbers);
		query.setParameter("gameName", name);
		int success = query.executeUpdate();
    }
    
   @Override
    @ReadThroughSingleCache(namespace = "LottoGame", expiration = 3600)
    public LottoGame get(@ParameterValueKeyProvider Long id) {
    	return super.get(id);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "LottoGame")
    public LottoGame save(LottoGame b) {
        return super.save(b);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "LottoGame")
    public void remove(Long id) {
        super.remove(id);
    }
    
    @Override
    @InvalidateSingleCache(namespace = "LottoGame")    
    public void remove(LottoGame obj) {
    	super.remove(obj);
    } 
}
