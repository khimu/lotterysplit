package com.share.lottery.webapp.controller;

import java.io.Serializable;

public class GroupForm implements Serializable {

	private String groupName;
	
	private String memberEmails;
	
	private String action = "list";

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemberEmails() {
		return memberEmails;
	}

	public void setMemberEmails(String memberEmails) {
		this.memberEmails = memberEmails;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
