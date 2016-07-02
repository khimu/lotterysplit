package com.share.lottery.model;

import java.util.Set;

public class EmailBO {
	
	private Set<String> emails;
	private Set<String> ignore;
	// the name used
	private String type;

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getIgnore() {
		return ignore;
	}

	public void setIgnore(Set<String> ignore) {
		this.ignore = ignore;
	}

}
