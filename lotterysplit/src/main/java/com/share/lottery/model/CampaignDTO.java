package com.share.lottery.model;

import java.io.Serializable;
import java.util.List;

public class CampaignDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String campaignName;

	private String brand;

	private List<String> pool;

	private Long lotteryTicketId;

	private Long userId;

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public List<String> getPool() {
		return pool;
	}

	public void setPool(List<String> pool) {
		this.pool = pool;
	}

	public Long getLotteryTicketId() {
		return lotteryTicketId;
	}

	public void setLotteryTicketId(Long lotteryTicketId) {
		this.lotteryTicketId = lotteryTicketId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
