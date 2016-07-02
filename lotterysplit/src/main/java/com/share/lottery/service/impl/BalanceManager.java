package com.share.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.IBalanceDao;
import com.share.lottery.model.Balance;
import com.share.lottery.service.IBalanceManager;

@Transactional("lotterySplitTransactionManager")
@Service("balanceManager")
public class BalanceManager extends GenericManagerImpl<Balance, Long> implements IBalanceManager {

	private IBalanceDao balanceDao;
	
	@Autowired
	public BalanceManager(IBalanceDao balanceDao){
		super(balanceDao);
		this.balanceDao = balanceDao;
	}
	
	@Override
	public Balance findBalanceByUserId(Long userId) throws UsernameNotFoundException {
		return balanceDao.findBalanceByUserId(userId);
	}

}
