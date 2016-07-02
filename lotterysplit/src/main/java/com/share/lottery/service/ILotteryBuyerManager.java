package com.share.lottery.service;

import java.util.List;

import com.share.lottery.model.LotteryBuyer;

public interface ILotteryBuyerManager extends GenericManager<LotteryBuyer, Long> {

	public Integer getAllSoldCount(Long userId);
	
	public Integer getAllBoughtCount(Long buyerUserId);

	public List<LotteryBuyer> findAllSold(Long userId, int start);
	
	public List<LotteryBuyer> findAllBought(Long buyerUserId, int start);
	
}
