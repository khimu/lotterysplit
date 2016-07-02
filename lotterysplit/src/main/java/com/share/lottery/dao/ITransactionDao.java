package com.share.lottery.dao;

import java.util.List;

import com.share.lottery.model.Transaction;

public interface ITransactionDao extends GenericDao<Transaction, Long> {

	public List<Transaction> findTransactionsByUserId(Long userId, int start);
	
	public Integer getAllMyTransactionsCount(Long userId);
	
	
}
