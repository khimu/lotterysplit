package com.share.lottery.dao;

import java.util.List;

import com.share.lottery.model.LotteryTicket;

public interface ILotteryTicketDao extends GenericDao<LotteryTicket, Long> {

	public LotteryTicket getRandomFreeLottery();
	
	public List<LotteryTicket> getAll(int start);

	public Integer getAllCount();

	public Integer getAllOthersCount(Long userId);

	public Integer getAllMyListCount(Long userId);

	public List<LotteryTicket> getAllTicketsInList(int start, List<Long> lotteryIds);
	
	public List<LotteryTicket> getAllOthers(int start, Long userId);

	public List<LotteryTicket> getAllMyList(int start, Long userId);
	
	public Integer getAllOrderCount();

	public List<LotteryTicket> getAllOrders(int start);

}
