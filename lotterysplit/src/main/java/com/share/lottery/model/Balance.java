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
@Table(name = "balance")
public class Balance extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer version;
	private Long userId;
	private BigDecimal amount;
	private Date lastModified;

	private Date createdOn;

	private Integer listItemCount;
	private Integer spentCount;
	private Integer earnedCount;

	private Integer transactionCount;

	private BigDecimal billable;
	private Date lastBilledDate;

	private Date lastCashOutDate;
	private BigDecimal lastCashOutAmount;

	private BigDecimal earned;

	private BigDecimal spent;
	
	public Balance(){
		this.setSpent(new BigDecimal(0.00));
		this.setEarned(new BigDecimal(0.00));
		this.setLastCashOutAmount(new BigDecimal(0.00));
		this.setBillable(new BigDecimal(0.00));
		this.setListItemCount(0);
		this.setEarnedCount(0);
		this.setSpentCount(0);
		this.setTransactionCount(0);
	}

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

	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "last_modified")
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Column(name = "transaction_count")
	public Integer getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
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

	@Column(name = "billable")
	public BigDecimal getBillable() {
		return billable;
	}

	public void setBillable(BigDecimal billable) {
		this.billable = billable;
	}

	@Column(name = "last_billed_date")
	public Date getLastBilledDate() {
		return lastBilledDate;
	}

	public void setLastBilledDate(Date lastBilledDate) {
		this.lastBilledDate = lastBilledDate;
	}

	@Column(name = "last_cash_out_date")
	public Date getLastCashOutDate() {
		return lastCashOutDate;
	}

	public void setLastCashOutDate(Date lastCashOutDate) {
		this.lastCashOutDate = lastCashOutDate;
	}

	@Column(name = "last_cash_out_amount")
	public BigDecimal getLastCashOutAmount() {
		return lastCashOutAmount;
	}

	public void setLastCashOutAmount(BigDecimal lastCashOutAmount) {
		this.lastCashOutAmount = lastCashOutAmount;
	}

	@Column(name = "earned")
	public BigDecimal getEarned() {
		return earned;
	}

	public void setEarned(BigDecimal earned) {
		this.earned = earned;
	}

	@Column(name = "spent")
	public BigDecimal getSpent() {
		return spent;
	}

	public void setSpent(BigDecimal spent) {
		this.spent = spent;
	}

	@Column(name = "list_item_count")
	public Integer getListItemCount() {
		return listItemCount;
	}

	public void setListItemCount(Integer listItemCount) {
		this.listItemCount = listItemCount;
	}

	@Column(name = "spent_count")
	public Integer getSpentCount() {
		return spentCount;
	}

	public void setSpentCount(Integer spentCount) {
		this.spentCount = spentCount;
	}

	@Column(name = "earned_count")
	public Integer getEarnedCount() {
		return earnedCount;
	}

	public void setEarnedCount(Integer earnedCount) {
		this.earnedCount = earnedCount;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
