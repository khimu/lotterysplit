package com.share.lottery.model;

import java.io.Serializable;

public class PaymentReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private Double beginningBalance;

	private Double endingBalance;
	
	private Double paymentReceived;
	
	public PaymentReportDTO(){}
	
	public PaymentReportDTO(Double beginningBalance, Double endingBalance, Double paymentReceived){
		this.beginningBalance = beginningBalance;
		this.endingBalance = endingBalance;
		this.paymentReceived = paymentReceived;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getBeginningBalance() {
		return beginningBalance;
	}

	public void setBeginningBalance(Double beginningBalance) {
		this.beginningBalance = beginningBalance;
	}

	public Double getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(Double endingBalance) {
		this.endingBalance = endingBalance;
	}

	public Double getPaymentReceived() {
		return paymentReceived;
	}

	public void setPaymentReceived(Double paymentReceived) {
		this.paymentReceived = paymentReceived;
	}

}
