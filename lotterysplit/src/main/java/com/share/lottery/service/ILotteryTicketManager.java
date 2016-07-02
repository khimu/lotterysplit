package com.share.lottery.service;

import java.util.List;

import com.share.lottery.model.LotteryTicket;

public interface ILotteryTicketManager extends GenericManager<LotteryTicket, Long> {
	
	public LotteryTicket getRandomFreeLottery();

	public List<LotteryTicket> getAll(int start);
	
	public List<LotteryTicket> getAllOthers(int start, Long userId);
	
	public List<LotteryTicket> getAllMyList(int start, Long userId);
	
	public Integer getAllCount();
	
	public Integer getAllOthersCount(Long userId);
	
	public Integer getAllMyListCount(Long userId);
	
	public Integer getAllOrderCount();

	public List<LotteryTicket> getAllOrders(int start);
	
	public List<LotteryTicket> getAllTicketsInList(int start, List<Long> lotteryIds);
}
