package com.share.lottery.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionBO implements Serializable {
	
	public final static int CREDIT = 0;
	public final static int DEBIT = 1;

	private static final long serialVersionUID = 1L;

	private String _id;

	private Long userId;

	private Double amount;

	private int type;

	private Date createdOn;
	
	public TransactionBO(String id, Long userId, Double amount, int type, Date createdOn){
		this._id = id;
		this.userId = userId;
		this.amount = amount;
		this.type = type;
		this.createdOn = createdOn;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public static int getCredit() {
		return CREDIT;
	}

	public static int getDebit() {
		return DEBIT;
	}

}
