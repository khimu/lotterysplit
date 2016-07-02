package com.share.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.Constants;
import com.share.lottery.dao.ILotteryBuyerDao;
import com.share.lottery.dao.ILotteryTicketDao;
import com.share.lottery.dao.ILottoGameDao;
import com.share.lottery.dao.UserDao;
import com.share.lottery.model.LotteryBuyer;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.LottoGame;
import com.share.lottery.model.User;
import com.share.lottery.service.ILottoGameManager;
import com.share.lottery.service.ISpyMemcachedHandler;

@Transactional("lotterySplitTransactionManager")
@Service("lottoGameManager")
public class LottoGameManager extends GenericManagerImpl<LottoGame, Long> implements ILottoGameManager {
			
	private ILottoGameDao lottoGameDao;
	
	//@Autowired
	//private ISpyMemcachedHandler memcachedHandler;
	
	@Autowired
	private ILotteryBuyerDao lotteryBuyerDao;
	
	@Autowired
	private ILotteryTicketDao lotteryTicketDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	public LottoGameManager(ILottoGameDao lottoGameDao){
		super(lottoGameDao);
		this.lottoGameDao = lottoGameDao;
	}
	
	public void updateDrawing(String name, String winningAmount, String numbers, String prevDrawingDate, String nextDrawingDate){
		lottoGameDao.updateDrawing(name, winningAmount, numbers, prevDrawingDate, nextDrawingDate);
	}
	
	public void getLandingPageData(Map<String, Object> model){
		List<LotteryBuyer> buyers = lotteryBuyerDao.getAll();
		
		List<LotteryTicket> topListers = lotteryTicketDao.getAll();

		model.put("gamblers", buyers);
		model.put("topListers", topListers);
		
		List<User> players = userDao.getPalyers();
		
		/*
		List<User> players = (List<User>) memcachedHandler.getObject(Constants.RANDOM_PLAYERS);
		if(topListers == null){
			players = userDao.getPalyers();
			memcachedHandler.cacheObject(Constants.RANDOM_PLAYERS, players);
		}
		*/

		model.put("payers", players);
	}

}
