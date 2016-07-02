package com.share.lottery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
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
@Table(name = "lottery_buyer")
public class LotteryBuyer extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer version;
	
	private Boolean paid;
	
	private Long userId;
	
	private Long buyerUserId;
	private Long lotteryTicketId;
	
	private String referralCode;
	private String numbers;
	private BigDecimal cost;
	private Date ticketDate;
	private String facebookId;
	private String sellerFacebookId;
	private Date createdOn;

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
	
	@Column(name = "buyer_user_id")
	public Long getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(Long buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@Column(name = "cost")
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name = "ticket_date")
	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	@Column(name = "paid")
	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	@Column(name = "lottery_ticket_id")
	public Long getLotteryTicketId() {
		return lotteryTicketId;
	}

	public void setLotteryTicketId(Long lotteryTicketId) {
		this.lotteryTicketId = lotteryTicketId;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
		
	@Column(name = "numbers")
	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	@Column(name = "referral_code")
	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	@Column(name = "facebook_id")
	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	@Column(name = "seller_facebook_id")
	public String getSellerFacebookId() {
		return sellerFacebookId;
	}

	public void setSellerFacebookId(String sellerFacebookId) {
		this.sellerFacebookId = sellerFacebookId;
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
