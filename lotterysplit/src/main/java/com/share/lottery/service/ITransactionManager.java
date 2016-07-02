package com.share.lottery.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.share.lottery.exceptions.BuyException;
import com.share.lottery.exceptions.ListException;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.Payment;
import com.share.lottery.model.Transaction;

public interface ITransactionManager extends GenericManager<Transaction, Long> {
	
	public List<Transaction> findTransactionsByUserId(Long userId, int start);
	
	public Integer getAllMyTransactionsCount(Long userId);
	
	public void payment(Payment payment, Long userId);
	
	public Long listLotteryTicket(Long userId, LotteryTicket lotteryTicket) throws ListException;
	
	public Long orderLotteryTicket(Long userId, LotteryTicket lotteryTicket) throws ListException;
	
	public Integer buy(Long userId, Long buyerUserId, Long lotteryTicketId, Integer splitNumber, BigDecimal cost, String numbers, Date ticketDate) throws BuyException;

	public void fbLikeReward(Long userId);
}
