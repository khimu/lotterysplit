package com.share.lottery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.code.ssm.api.CacheKeyMethod;

@Entity
@Table(name = "transaction_activity")
public class Transaction extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static int CREDIT = 0;
	public final static int DEBIT = 1;
	public final static int PAYMENT = 2;
	public final static int CASH_OUT = 3;
	public final static int FEE = 4;
	public final static int LIST_FEE = 5;
	public final static int REWARD = 6;

	private Long id;

	private Integer version;
	
	private Long userId;
	
    private BigDecimal currentBalance;
    
    private BigDecimal amount;
 
    private Date createdOn;
    
    private Long lotteryTicketId;
    
    private Integer type;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@CacheKeyMethod
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "current_balance")
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "lottery_ticket_id")
	public Long getLotteryTicketId() {
		return lotteryTicketId;
	}

	public void setLotteryTicketId(Long lotteryTicketId) {
		this.lotteryTicketId = lotteryTicketId;
	}

	@Column(name = "transaction_type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
