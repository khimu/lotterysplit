package com.share.lottery.mongo.service;

import com.share.lottery.model.EmailBO;

public interface IEmailService {
	
	public int getCount(String type);
	
	public EmailBO getEmails(String type, int pageIndex);
	
	public void save(String type, String email);
	
	public void delete(String type);
	
	public void ignore(String type, String email);
	
	public void replace(String type, EmailBO emailBo);
	
}
