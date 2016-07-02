package com.share.lottery.service.impl;

import com.github.sendgrid.SendGrid;

public class SendGridEmailHandler {

	public static void send(String email, String msg, String subject){
		SendGrid sendgrid = new SendGrid("splitlottery", "KU*777zt");

		sendgrid.addTo(email);
		sendgrid.setFrom("info.splitlottery@gmail.com");
		sendgrid.setSubject(subject);
		sendgrid.setText(msg);

		sendgrid.send();
	}
}
