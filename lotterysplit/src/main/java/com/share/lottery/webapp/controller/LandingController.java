package com.share.lottery.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.ILotteryBuyerManager;
import com.share.lottery.service.ILotteryTicketManager;
import com.share.lottery.service.ILottoGameManager;
import com.share.lottery.service.ITransactionManager;
import com.share.lottery.service.UserManager;
import com.share.lottery.webapp.form.LoginForm;

@Controller
@RequestMapping("/landing.htm")
public class LandingController extends BaseController {

	@Autowired
	private ILotteryTicketManager lotteryTicketService;
	
	@Autowired
	private ITransactionManager transactionManager;
	
	@Autowired
	private ILottoGameManager lottoGameManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ILotteryBuyerManager lotteryBuyerManager;
	
	@Autowired
	private IBalanceManager balanceManager;
	
	public LandingController() {
		super(LandingController.class);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView processShowFormRequest(HttpServletRequest request, Map<String, Object> model) {
		lottoGameManager.getLandingPageData(model);
		setModel(model);
		return new ModelAndView("landing", model);
	}

}
