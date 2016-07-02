package com.share.lottery.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class SessionDTO implements Serializable {

	private static final long serialVersionUID = -1727891670947001475L;

	private Long userId;

	private String username;

	private String referralCode;

	private boolean admin;
	
	private boolean campaigner;

	public SessionDTO() {
	}

	public SessionDTO(Long userId, String email, String referralCode, boolean admin, boolean isCampaigner) {
		this.userId = userId;
		this.username = email;
		this.referralCode = referralCode;
		this.admin = admin;
		this.campaigner = isCampaigner;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isCampaigner() {
		return campaigner;
	}

	public void setCampaigner(boolean campaigner) {
		this.campaigner = campaigner;
	}

}
