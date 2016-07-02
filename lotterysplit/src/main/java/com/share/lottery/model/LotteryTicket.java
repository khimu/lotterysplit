package com.share.lottery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;

import com.google.code.ssm.api.CacheKeyMethod;

@Entity
@Table(name = "lottery_ticket")
public class LotteryTicket extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer version;
	
	private Long userId;
	private String state;
	private String ticketNumber;
	private String lottoGameId;
	private String numbers;
	private String lottoGameName;
	private String groupName;
	
	private String referralCode;
	
	
	private Integer available;
	
	/*
	 * the digits for playing
	 */
	private Integer slotSize;
	
	/*
	 * original buyer of ticket pays 10% of original ticket cost
	 * 
	 * how many ways to split the number
	 */
	private Integer splitNumber;
	
	/* 
	 * The number of buyers so far
	 * 
	 * splitNumber - buyerCount = 0, this ticket is no longer available
	 */
	private Integer buyerCount;
	
	private BigDecimal cost;
	private Date ticketDate;
	private Boolean flag;
	
	// implies not bought yet
	private Boolean alreadyPurchased;
	
	private byte[] imageBytes;
	
	private Date createdOn;
	private Date lastModified;
	private Boolean deleted;
	
	private String facebookId;
	
	private String iconByte;

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

	@Column(name = "last_modified")
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "ticket_number")
	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	@Column(name = "lotto_game_id")
	public String getLottoGameId() {
		return lottoGameId;
	}

	public void setLottoGameId(String lottoGameId) {
		this.lottoGameId = lottoGameId;
	}

	@Column(name = "numbers")
	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	@Column(name = "slot_size")
	public Integer getSlotSize() {
		return slotSize;
	}

	public void setSlotSize(Integer slotSize) {
		this.slotSize = slotSize;
	}

	@Column(name = "split_number")
	public Integer getSplitNumber() {
		return splitNumber;
	}

	public void setSplitNumber(Integer splitNumber) {
		this.splitNumber = splitNumber;
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

	@Column(name = "flag")
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Column(name = "already_purchased")
	public Boolean getAlreadyPurchased() {
		return alreadyPurchased;
	}

	public void setAlreadyPurchased(Boolean alreadyPurchased) {
		this.alreadyPurchased = alreadyPurchased;
	}

	@Lob
	@Column(name = "image_bytes")
	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="lotto_game_name")
	public String getLottoGameName() {
		return lottoGameName;
	}

	public void setLottoGameName(String lottoGameName) {
		this.lottoGameName = lottoGameName;
	}

	@Column(name = "deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "buyer_count")
	public Integer getBuyerCount() {
		return buyerCount;
	}

	public void setBuyerCount(Integer buyerCount) {
		this.buyerCount = buyerCount;
	}
	
	@Transient
	@JsonIgnore
	public Integer getShowBuyButton() {
		if(splitNumber - buyerCount > 0 || flag == false || ticketDate.after(new Date())) {
			return 1;
		}
		return 0;
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
	
	@Lob
	@Column(name = "icon_byte")
	public String getIconByte() {
		return iconByte;
	}

	public void setIconByte(String iconByte) {
		this.iconByte = iconByte;
	}
	
	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Formula("split_number - buyer_count") 
	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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
