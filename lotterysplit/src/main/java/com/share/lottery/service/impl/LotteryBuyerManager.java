package com.share.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.ILotteryBuyerDao;
import com.share.lottery.model.LotteryBuyer;
import com.share.lottery.service.ILotteryBuyerManager;

@Transactional("lotterySplitTransactionManager")
@Service("lotteryBuyerManager")
public class LotteryBuyerManager extends GenericManagerImpl<LotteryBuyer, Long> implements ILotteryBuyerManager {

	private ILotteryBuyerDao lotteryBuyerDao;
	
	@Autowired
	public LotteryBuyerManager(ILotteryBuyerDao lotteryBuyerDao){
		super(lotteryBuyerDao);
		this.lotteryBuyerDao = lotteryBuyerDao;
	}

	@Override
	public Integer getAllSoldCount(Long userId) {
		return lotteryBuyerDao.getAllSoldCount(userId);
	}

	@Override
	public Integer getAllBoughtCount(Long buyerUserId) {
		return lotteryBuyerDao.getAllBoughtCount(buyerUserId);
	}

	@Override
	public List<LotteryBuyer> findAllSold(Long userId, int start) {
		return lotteryBuyerDao.findAllSold(userId, start);
	}

	@Override
	public List<LotteryBuyer> findAllBought(Long buyerUserId, int start) {
		return lotteryBuyerDao.findAllBought(buyerUserId, start);
	}

}
