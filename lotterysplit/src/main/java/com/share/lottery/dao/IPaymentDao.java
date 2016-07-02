package com.share.lottery.dao;

import java.util.List;

import com.share.lottery.model.Payment;

public interface IPaymentDao extends GenericDao<Payment, Long> {

	public List<Payment> findPaymentsByUserId(Long userId, int start);
	
	public Integer getAllMyPaymentsCount(Long userId);
	
	
}
