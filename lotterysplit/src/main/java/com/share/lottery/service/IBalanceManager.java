package com.share.lottery.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.share.lottery.model.Balance;

public interface IBalanceManager extends GenericManager<Balance, Long> {
	
	public Balance findBalanceByUserId(Long userId) throws UsernameNotFoundException;

}
