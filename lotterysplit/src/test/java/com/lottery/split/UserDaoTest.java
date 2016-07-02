package com.lottery.split;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.share.lottery.dao.UserDao;
import com.share.lottery.model.User;

public class UserDaoTest extends BaseDaoTestCase {

	@Autowired
	private UserDao dao;
	
	@Test
	public void testUserPassword(){
		String password = dao.getUserPassword(2L);
		System.out.println(password);
	}
	
	public void testReferralCode(){
		dao.updateReferralCount("xesxx3xx");
		User user = dao.findByReferralCode("xesxx3xx");
		Assert.assertEquals(1, user.getAwarded());
	}
	
	@Test
	public void testfindAllUserForEmails(){
		//List<Long> users = dao.findAllUserForEmails(new String[] {"khimq@khim.com", "khima@khim.com"});
		//Assert.assertNotNull(users);
		//Assert.assertEquals(2, users.size());
	}
}
