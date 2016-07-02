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
@Table(name = "payment")
public class Payment extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer version;
	private Long userId;
	private String transactionId;
	private BigDecimal amount;
	private String transactionMessage;
	private Integer success;
	private Date createdOn;
	private String invoiceNumber;
	
	public Payment(){}
	
	public Payment(Long userId, String transactionId, BigDecimal amount, String transactionMessage, Date createdOn, Integer success, String invoiceNumber){		
		this.userId = userId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.transactionMessage = transactionMessage;
		this.createdOn = createdOn;
		this.success = success;
		this.invoiceNumber = invoiceNumber;
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

	@Column(name = "transaction_id", unique = true)
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "transaction_message")
	public String getTransactionMessage() {
		return transactionMessage;
	}

	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "success")
	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	@Column(name = "invoice_number")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
