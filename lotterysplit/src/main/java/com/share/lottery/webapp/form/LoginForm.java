/*
 * @(#)com.icsmobile.faadplatform.form
 * 
 *  Copyright 2011-2012 ICS Mobile, Inc. All rights reserved
 *
 *
 * JDK version : 6.0
 * Version History : 1.0    Mar 30, 2011
 */
package com.share.lottery.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * It defines the fields in the loginForm for FAAD webapp
 *
 * @author waqas.sadiq
 */
public class LoginForm {

	@NotEmpty(message = "Username cannot be empty")
	@Size(min = 1, max = 50, message = "Username cannot exceed 50 characters")
	private String username;
	
	@NotEmpty(message = "Password cannot be empty")
	@Size(min = 1, max = 30, message = "Password cannot exceed 30 characters")
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
