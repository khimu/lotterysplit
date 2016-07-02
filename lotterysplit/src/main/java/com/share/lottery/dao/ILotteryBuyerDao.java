package com.share.lottery.dao;

import java.util.List;

import com.share.lottery.model.LotteryBuyer;

public interface ILotteryBuyerDao extends GenericDao<LotteryBuyer, Long> {
		
	public Integer getAllSoldCount(Long userId);
	
	public Integer getAllBoughtCount(Long buyerUserId);

	public List<LotteryBuyer> findAllSold(Long userId, int start);
	
	public List<LotteryBuyer> findAllBought(Long buyerUserId, int start);
	
	public LotteryBuyer findAlreadyBought(Long buyerUserId, Long lotteryId);
}
