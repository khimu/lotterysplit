package com.share.lottery.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.share.lottery.model.SessionDTO;
import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.IPaymentManager;

@Controller
@RequestMapping("report")
public class TransactionsController extends BaseController {
	
	@Autowired
	private IBalanceManager balanceManager;
	
	@Autowired
	private IPaymentManager paymentManager;

	public TransactionsController() {
		super(TransactionsController.class);
	}
	
	@RequestMapping(value="payments.htm", method = RequestMethod.GET)
	public String transactions(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, Map<String, Object> model, HttpServletRequest request) {	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		
		model.put("payments", paymentManager.findPaymentsByUserId(session.getUserId(), (start - 1) * 50));
    	model.put("totalCount", paymentManager.getAllMyPaymentsCount(session.getUserId()));
    	model.put("pageSize", 50);
    	model.put("start", start);
    	setModel(model, session.getReferralCode());
		return "report/payments";
	}

}
