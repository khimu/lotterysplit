package com.share.lottery.webapp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.Constants;
import com.share.lottery.model.GroupDTO;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IGroupLotteryService;
import com.share.lottery.service.ILotteryTicketManager;
import com.share.lottery.service.ILottoGameManager;
import com.share.lottery.service.ITransactionManager;
import com.share.lottery.service.UserManager;
import com.share.lottery.util.EMailUtil;
import com.share.lottery.webapp.util.RequestUtil;

@Controller
@RequestMapping("/ticket")
public class SplitTicketController extends BaseController {
	
	@Autowired
	private UserManager userService;
	
	@Autowired
	private ILotteryTicketManager lotteryTicketService;
	
	@Autowired
	private ITransactionManager transactionManager;
	
	@Autowired
	private ILottoGameManager lottoGameManager;
	
	@Autowired
	private IGroupLotteryService groupLotteryService;
	
	private String templateName = "referralScoreEmail.vm";


	public SplitTicketController(){
		super(SplitTicketController.class);
	}
	
	@ModelAttribute("lotteryTicket")
	public LotteryTicket populateLotteryTicket() {
		LotteryTicket ticket = new LotteryTicket();
		ticket.setCost(new BigDecimal(0));
		return ticket;
	}
	
    @RequestMapping(value="/{lotteryTicketId}", method = RequestMethod.GET)
    public void getLottoTicket(HttpServletRequest request, HttpServletResponse response,  @PathVariable("lotteryTicketId") Long lotteryTicketId) {
		LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
		byte[] imageBytes = null;
		try {
			if (lotteryTicketId != null) {
				imageBytes = ticket.getImageBytes();
			}

			if (imageBytes != null) {
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				response.setContentLength(imageBytes.length);
				response.getOutputStream().write(imageBytes);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
    }	
	
    /*
     * No editing
     */
    @RequestMapping(value="/split.htm", method = RequestMethod.GET)
    public ModelAndView listLotto(HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		String groupName = request.getParameter("groupName");
		
		LotteryTicket ticket = new LotteryTicket();
		if(StringUtils.isNotEmpty(groupName)){
			ticket.setCost(new BigDecimal(1.00));
			ticket.setGroupName(groupName);
		}else{
			ticket.setCost(new BigDecimal(1.00));
			ticket.setGroupName("public");
		}
		
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("games", lottoGameManager.getAll());
    	model.put("lotteryTicket", ticket);
    	populateLotteryTicket();
    	
    	setModel(model, session.getReferralCode());
        return new ModelAndView("ticket/split", model);
    }
    
    /*
     * No editing
     */
    @RequestMapping(value="/order.htm", method = RequestMethod.GET)
    public ModelAndView order(HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		String groupName = request.getParameter("groupName");
		
    	Map<String, Object> model = new HashMap<String, Object>();

    		LotteryTicket ticket = new LotteryTicket();
    		if(StringUtils.isNotEmpty(groupName)){
    			ticket.setCost(new BigDecimal(1.00));
    			ticket.setGroupName(groupName);
    		}else{
    			ticket.setCost(new BigDecimal(1.00));
    			ticket.setGroupName("public");
    		}
    		
        	model.put("games", lottoGameManager.getAll());
        	model.put("lotteryTicket", ticket);
    	setModel(model, session.getReferralCode());
        return new ModelAndView("ticket/order", model);
    }
 
    @RequestMapping(value="/order.htm", method = RequestMethod.POST)
    public String submitOrder( @ModelAttribute("lotteryTicket") LotteryTicket lotteryTicket, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {

		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		
		String date = request.getParameter("ticketDrawDate");
		
		if(StringUtils.isBlank(date)){
			model.put("error", "Ticket draw date cannot be blank");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
		}
		
        SimpleDateFormat dateFormat = 
                new SimpleDateFormat(getText("date.format", request.getLocale()));
            dateFormat.setLenient(false);
            
        Date ticketDrawDate = dateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(ticketDrawDate);
        cal.add(Calendar.HOUR, 21);

        lotteryTicket.setTicketDate(cal.getTime());
        
        if(lotteryTicket.getSplitNumber() > 100){
			model.put("error", "Pool size cannot exceed 100");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/order";
        }
        
        Long lotteryTicketId = 0L;
        try {
        	lotteryTicketId = transactionManager.orderLotteryTicket(session.getUserId(), lotteryTicket);
        	User user = userManager.get(session.getUserId());
        	EMailUtil.sendOrderNotification(templateName, message, mailEngine, user, "New Lotto Order: Numbers: " + lotteryTicket.getNumbers() + " Draw Date: " + lotteryTicket.getTicketDate() + " Lottery Game : " + lotteryTicket.getLottoGameName());
		}catch(Exception e){
			List<String> errors = new ArrayList<String>();
			errors.add(e.getMessage());
			model.put("errors", errors);
			return "error";
		}
        
        if(lotteryTicketId == Constants.ListLottery.EXPIRED) {
			model.put("error", "Lottery ticket has expired");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/order";
        }else if(lotteryTicketId == Constants.ListLottery.UNSUPPORTED_BARCODE) {
			model.put("error", "Lottery ticket barcode is not yet supported");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/order";
        }
 
    	return "redirect:" + request.getContextPath() + "/lottery/ticket/order/detail/" + lotteryTicketId;
    }
    
    @RequestMapping(value="/split.htm", method = RequestMethod.POST)
    public String saveLottoGame( @ModelAttribute("lotteryTicket") LotteryTicket lotteryTicket, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {

		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		
		String date = request.getParameter("ticketDrawDate");
		
		if(StringUtils.isBlank(date)){
			model.put("error", "Ticket draw date cannot be blank");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
		}
		
        SimpleDateFormat dateFormat = 
                new SimpleDateFormat(getText("date.format", request.getLocale()));
            dateFormat.setLenient(false);
            
        Date ticketDrawDate = dateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(ticketDrawDate);
        cal.add(Calendar.HOUR, 21);

        lotteryTicket.setTicketDate(cal.getTime());
        
        if(lotteryTicket.getSplitNumber() > 100){
			model.put("error", "Group size cannot exceed 100");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
        }
        
        Long lotteryTicketId = 0L;
        try {
        	lotteryTicketId = transactionManager.listLotteryTicket(session.getUserId(), lotteryTicket);
		}catch(Exception e){
			List<String> errors = new ArrayList<String>();
			errors.add(e.getMessage());
			model.put("errors", errors);
			return "error";
		}
        
        if(lotteryTicketId == Constants.ListLottery.DUPLICATE){
			model.put("error", "Lottery ticket already listed");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
        }else if(lotteryTicketId == Constants.ListLottery.EXPIRED) {
			model.put("error", "Lottery ticket has expired");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
        }else if(lotteryTicketId == Constants.ListLottery.UNSUPPORTED_BARCODE) {
			model.put("error", "Lottery ticket barcode is not yet supported");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
        }
 
    	return "redirect:" + request.getContextPath() + "/lottery/ticket/detail/" + lotteryTicketId;
    }
    
    @RequestMapping(value="/order/detail/{lotteryTicketId}", method = RequestMethod.GET)
    public ModelAndView orderdetail( Map<String, Object> model, HttpServletRequest request, @PathVariable("lotteryTicketId") Long lotteryTicketId) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
    	LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
    	
    	User user = userManager.get(ticket.getUserId());
        
    	model.put("lister", user);
        model.put("ticket", ticket);
        setModel(model, session.getReferralCode());
    	return new ModelAndView("ticket/orderdetail", model);
    }
    
    @RequestMapping(value="/detail/{lotteryTicketId}", method = RequestMethod.GET)
    public ModelAndView detail( Map<String, Object> model, HttpServletRequest request, @PathVariable("lotteryTicketId") Long lotteryTicketId) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
    	LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
    	
    	if(session.getUserId().equals(ticket.getUserId()) == false){
    		return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/ticket/list.htm?start=1");
    	}
    	
    	User user = userManager.get(ticket.getUserId());
        
    	model.put("isBusiness", session.isCampaigner());
    	model.put("lister", user);
        model.put("ticket", ticket);
        setModel(model, session.getReferralCode());
    	return new ModelAndView("ticket/detail", model);
    }
    
    @RequestMapping(value="/delete/{lotteryTicketId}", method = RequestMethod.GET)
    public void delete(HttpServletRequest request, HttpServletResponse response,  @PathVariable("lotteryTicketId") Long lotteryTicketId) {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			ObjectNode node = objectMapper.createObjectNode();
			node.put("result", "authentication failed");
			this.output(node.toString(), response);
		}
		
    	LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
    	if(session.getUserId().equals(ticket.getUserId()) == false){
			ObjectNode node = objectMapper.createObjectNode();
			node.put("result", "permission denied");
			this.output(node.toString(), response);
    	}
    		
		ticket.setDeleted(true);
		lotteryTicketService.save(ticket);
		ObjectNode node = objectMapper.createObjectNode();
		node.put("result", "deleted");
		this.output(node.toString(), response);
    }	
    
    @RequestMapping(value="/flag/{lotteryTicketId}", method = RequestMethod.GET)
    public void flag(HttpServletRequest request, HttpServletResponse response,  @PathVariable("lotteryTicketId") Long lotteryTicketId) {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			ObjectNode node = objectMapper.createObjectNode();
			node.put("result", "authentication failed");
			this.output(node.toString(), response);
		}
    	LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
		ticket.setFlag(true);
		lotteryTicketService.save(ticket);
		ObjectNode node = objectMapper.createObjectNode();
		node.put("result", "flagged");
		this.output(node.toString(), response);
    }	
    
    @RequestMapping(value="/list.htm", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, HttpServletRequest request) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		// all groups own by user
		List<GroupDTO> groups = groupLotteryService.getGroups(session.getUserId());
		List<Long> tickets = new ArrayList<Long>();
		if(groups != null){
			for(GroupDTO group : groups){
				if(group.getTickets() != null && group.getTickets().isEmpty() == false){
					tickets.addAll(group.getTickets());
				}
			}
		}
		
		// groups user was invited to
		List<GroupDTO> groupsInvitedTo = groupLotteryService.getGroups(session.getUserId());
		if(groupsInvitedTo != null){
			for(GroupDTO group : groupsInvitedTo){
				if(group.getTickets() != null && group.getTickets().isEmpty() == false){
					tickets.addAll(group.getTickets());
				}
			}
		}
		
		GroupDTO group = groupLotteryService.getPublicGroup();
		if(group != null && group.getTickets() != null && group.getTickets().isEmpty() == false){
			tickets.addAll(group.getTickets());
		}
		
		if(tickets.isEmpty() == true){
	    	model.put("loggedInUserId", session.getUserId());
	    	model.put("tickets", new ArrayList<LotteryTicket>());
	    	model.put("totalCount", 0);
	    	model.put("pageSize", 50);
	    	model.put("start", start);
	    	setModel(model, session.getReferralCode());
	        return new ModelAndView("ticket/list", model);
		}
    	List<LotteryTicket> list = lotteryTicketService.getAllTicketsInList((start - 1) * 50, tickets);
    	model.put("loggedInUserId", session.getUserId());
    	model.put("tickets", list);
    	model.put("totalCount", tickets.size());
    	model.put("pageSize", 50);
    	model.put("start", start);
    	setModel(model, session.getReferralCode());
        return new ModelAndView("ticket/list", model);
    }
    
    @RequestMapping(value="/mylist.htm", method = RequestMethod.GET)
    public ModelAndView mylist(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
    	Map<String, Object> model = new HashMap<String, Object>();
    	Long userId = this.getSessionObject(request.getSession()).getUserId();
    	
    	List<LotteryTicket> list = lotteryTicketService.getAllMyList((start - 1) * 50, userId);
    	model.put("tickets", list);
    	
    	model.put("totalCount", lotteryTicketService.getAllMyListCount(userId));
    	model.put("pageSize", 50);
    	model.put("start", start);
    	setModel(model, session.getReferralCode());
        return new ModelAndView("ticket/mylist", model);
    }    
    
}
