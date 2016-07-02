package com.share.lottery.service;

import java.util.List;

import com.share.lottery.model.Payment;

public interface IPaymentManager extends GenericManager<Payment, Long> {
	
	public List<Payment> findPaymentsByUserId(Long userId, int start);
	
	public Integer getAllMyPaymentsCount(Long userId);
	
}
