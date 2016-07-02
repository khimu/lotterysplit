package com.share.lottery.model;

import java.io.Serializable;
import java.util.List;

public class GroupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupName;
	
	private List<Long> tickets;
	
	private List<Long> userIds;
	
	private List<String> emails;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Long> getTickets() {
		return tickets;
	}

	public void setTickets(List<Long> tickets) {
		this.tickets = tickets;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

}
