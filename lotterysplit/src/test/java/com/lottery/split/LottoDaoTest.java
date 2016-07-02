package com.lottery.split;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.share.lottery.dao.ILotteryTicketDao;
import com.share.lottery.model.LotteryTicket;

public class LottoDaoTest extends BaseDaoTestCase {

	@Autowired
	ILotteryTicketDao lotteryTicketDao;
	
	@Test
	public void testSave(){
		LotteryTicket lotto = new LotteryTicket();
		lotto.setAlreadyPurchased(false);
		lotto.setCost(new BigDecimal(1.00));
		lotto.setCreatedOn(new Date());
		lotto.setFlag(false);
		lotto.setUserId(1L);
		lotto.setState("CA");
		lotto.setTicketNumber("xxx"); 
		lotto.setTicketDate(new Date());
		lotto.setImageBytes("hello".getBytes());
		
		lotteryTicketDao.save(lotto);
	}
	 
	@Test
	public void testGetAll(){
		List<LotteryTicket> tickets = lotteryTicketDao.getAll(1);
		for(LotteryTicket t : tickets){
			System.out.println(t.getState());
		}
	}
}
