package com.share.lottery.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.Constants;
import com.share.lottery.model.LotteryBuyer;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.service.ILotteryBuyerManager;
import com.share.lottery.service.ILotteryTicketManager;
import com.share.lottery.service.ITransactionManager;

@Controller
@RequestMapping("/ticket")
public class BuyTicketController extends BaseController {
	
	@Autowired
	private ITransactionManager transactionManager;
	
	@Autowired
	private ILotteryTicketManager lotteryTicketManager;
	
	@Autowired
	private ILotteryBuyerManager lotteryBuyerManager;
	
	public BuyTicketController(){
		super(BuyTicketController.class);
	}
	
    @RequestMapping(value="/buy/{lotteryTicketId}", method = RequestMethod.GET)
    public ModelAndView buyForm(HttpServletRequest request, @PathVariable("lotteryTicketId") Long lotteryTicketId) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		LotteryTicket ticket = lotteryTicketManager.get(lotteryTicketId);

		Integer success = 0;
		try {
			success = transactionManager.buy(ticket.getUserId(), session.getUserId(), ticket.getId(), ticket.getSplitNumber(), ticket.getCost(), ticket.getNumbers(), ticket.getTicketDate());
			if(Constants.BuyLottery.FREEBIE == success){
				setModel(model, session.getReferralCode());
				model.put("ticket", ticket);
				model.put("message", "Freebie buy successful");
				return new ModelAndView("ticket/buy", model);
			}else if(Constants.BuyLottery.DUPLICATE_PURCHASE == success ){
				setModel(model, session.getReferralCode());
				model.put("ticket", ticket);
				model.put("message", "You have already joined this lottery pool.");
				return new ModelAndView("ticket/buy", model);
			}else if(Constants.BuyLottery.FREEBIE_ERROR == success ){
				setModel(model, session.getReferralCode());
				model.put("ticket", ticket);
				model.put("message", "System error during freebie buy");
				return new ModelAndView("ticket/buy", model);
			}
		}catch(Exception e){
			List<String> errors = new ArrayList<String>();
			errors.add(e.getMessage());
			model.put("errors", errors);
			return new ModelAndView("error");
		}

		if(success == Constants.BuyLottery.OUT_OF_CASH){
			setModel(model, session.getReferralCode());
			model.put("ticket", ticket);
			model.put("message", "Please add cash to your account");
			return new ModelAndView("ticket/buy", model);
		}
		if(success == Constants.BuyLottery.OUT_OF_TICKETS){
			setModel(model, session.getReferralCode());
			model.put("ticket", ticket);
			model.put("message", "Lottery ticket is no longer available");
			return new ModelAndView("ticket/buy", model);
		}		
		
		if(success == Constants.BuyLottery.REFERER_MAIL){
			logger.error("Unable to email referer 1000 to admin for user id " + session.getUserId());
		}
		
        return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/ticket/bought.htm", model);
    }
    
    @RequestMapping(value="/buydetail/{lotteryTicketId}", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, @PathVariable("lotteryTicketId") Long lotteryTicketId) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		LotteryTicket ticket = lotteryTicketManager.get(lotteryTicketId);
    	User user = userManager.get(ticket.getUserId());
        
    	model.put("lister", user);
		model.put("ticket", ticket);
		setModel(model, session.getReferralCode());
		
        return new ModelAndView("ticket/buy", model);
    }
    

    @RequestMapping(value="/bought.htm", method = RequestMethod.GET)
    public ModelAndView bought(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, HttpServletRequest request) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		List<LotteryBuyer> list = lotteryBuyerManager.findAllBought(session.getUserId(), (start - 1) * 50);

		model.put("tickets", list);
    	model.put("totalCount", lotteryBuyerManager.getAllBoughtCount(session.getUserId()));
    	model.put("pageSize", 50);
    	model.put("start", start);
    	setModel(model, session.getReferralCode());
		
        return new ModelAndView("ticket/bought", model);
    }
    
	@RequestMapping(value="sold.htm", method = RequestMethod.GET)
	public String transactions(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, Map<String, Object> model, HttpServletRequest request) {	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		
		List<LotteryBuyer> list = lotteryBuyerManager.findAllSold(session.getUserId(), (start - 1) * 50);

		model.put("tickets", list);
    	model.put("totalCount", lotteryBuyerManager.getAllSoldCount(session.getUserId()));
    	model.put("pageSize", 50);
    	model.put("start", start);
    	setModel(model, session.getReferralCode());
		
		return "ticket/sold";
	}
	
}
