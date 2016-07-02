package com.share.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.ILotteryTicketDao;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.service.ILotteryTicketManager;

@Transactional("lotterySplitTransactionManager")
@Service("lotteryTicketManager")
public class LotteryTicketManager extends GenericManagerImpl<LotteryTicket, Long> implements ILotteryTicketManager {

	private ILotteryTicketDao lotteryTicketDao;
	
	@Autowired
	public LotteryTicketManager(ILotteryTicketDao lotteryTicketDao){
		super(lotteryTicketDao);
		this.lotteryTicketDao = lotteryTicketDao;
	}
	
	public List<LotteryTicket> getAll(int start){
		return lotteryTicketDao.getAll(start);
	}
	
	public Integer getAllCount(){
		return lotteryTicketDao.getAllCount();
	}
	
	@Override
	public Integer getAllOthersCount(Long userId){
		return lotteryTicketDao.getAllOthersCount(userId);
	}
	
	@Override
	public Integer getAllMyListCount(Long userId){
		return lotteryTicketDao.getAllMyListCount(userId);
	}

	@Override
	public List<LotteryTicket> getAllOthers(int start, Long userId) {
		return lotteryTicketDao.getAllOthers(start, userId);
	}

	@Override
	public List<LotteryTicket> getAllMyList(int start, Long userId) {
		return lotteryTicketDao.getAllMyList(start, userId);
	}

	@Override
	public Integer getAllOrderCount() {
		return lotteryTicketDao.getAllOrderCount();
	}

	@Override
	public List<LotteryTicket> getAllOrders(int start) {
		return lotteryTicketDao.getAllOrders(start);
	}
	
	public List<LotteryTicket> getAllTicketsInList(int start, List<Long> lotteryIds){
		return lotteryTicketDao.getAllTicketsInList(start, lotteryIds);
	}

	@Override
	public LotteryTicket getRandomFreeLottery() {
		return lotteryTicketDao.getRandomFreeLottery();
	}

}
