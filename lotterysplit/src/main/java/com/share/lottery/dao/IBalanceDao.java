package com.share.lottery.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.share.lottery.model.Balance;

public interface IBalanceDao extends GenericDao<Balance, Long> {

	public Balance findBalanceByUserId(Long userId) throws UsernameNotFoundException;
	
}
