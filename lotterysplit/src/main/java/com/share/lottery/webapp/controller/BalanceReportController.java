package com.share.lottery.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.model.BalanceSheetDTO;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.mongo.service.IBalanceSheetService;

@Controller
@RequestMapping("/report/")
public class BalanceReportController extends BaseController {

	@Autowired
	private IBalanceSheetService balanceSheetService;

	public BalanceReportController() {
		super(PaymentController.class);
	}
	
	@RequestMapping(value="balance.htm", method = RequestMethod.GET)
	public ModelAndView report(Map<String, Object> model, HttpServletRequest request) {	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		BalanceSheetDTO sheet = balanceSheetService.getBalanceSheet(session.getUserId());
		
		model.put("sheet", sheet);
		setModel(model, session.getReferralCode());
		
		return new ModelAndView("report/balance", model);
		
	}
	
}
