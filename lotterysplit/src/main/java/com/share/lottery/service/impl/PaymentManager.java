package com.share.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.IPaymentDao;
import com.share.lottery.model.Payment;
import com.share.lottery.service.IPaymentManager;

@Transactional("lotterySplitTransactionManager")
@Service("paymentManager")
public class PaymentManager extends GenericManagerImpl<Payment, Long> implements IPaymentManager {

	IPaymentDao paymentDao;
	
	public PaymentManager(){}
	
	@Autowired
    public PaymentManager(@Qualifier("paymentDao") IPaymentDao paymentDao) {
        super(paymentDao);
        this.paymentDao = paymentDao;
    }
	
	public List<Payment> findPaymentsByUserId(Long userId, int start) {
		return this.paymentDao.findPaymentsByUserId(userId, start);
	}

	@Override
	public Integer getAllMyPaymentsCount(Long userId) {
		return this.paymentDao.getAllMyPaymentsCount(userId);
	}
}
