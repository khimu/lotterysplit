package com.lottery.split;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.share.lottery.mongo.service.IEmailService;

@ContextConfiguration( 
        locations = {"classpath:**/applicationContext-resources.xml",
                "classpath:**/applicationContext-dao.xml",
                "classpath:**/applicationContext-service.xml",
                "classpath:/test-mongoContext.xml"})
public class MongoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IEmailService emailService;

	@Test
	public void testCount(){
		System.out.println(emailService.getCount("ny"));
	}
}
