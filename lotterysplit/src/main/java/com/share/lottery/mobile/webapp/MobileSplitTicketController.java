package com.share.lottery.mobile.webapp;

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
import org.apache.cxf.jaxrs.provider.JSONUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.directwebremoting.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.share.lottery.webapp.controller.BaseController;

@Controller
@RequestMapping("/ticket")
public class MobileSplitTicketController extends BaseController {
	
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


	public MobileSplitTicketController(){
		super(MobileSplitTicketController.class);
	}
		
    /*
     * No editing
     */
    @RequestMapping(value="/split", method = RequestMethod.GET, produces={"application/json"})
    public @ResponseBody Map<String, Object> listLotto(HttpServletRequest request) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
    	/*
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ArrayList<Object>();
		}
		*/
		
		String groupName = request.getParameter("groupName");
		
		LotteryTicket ticket = new LotteryTicket();
		if(StringUtils.isNotEmpty(groupName)){
			ticket.setCost(new BigDecimal(1.00));
			ticket.setGroupName(groupName);
		}else{
			ticket.setCost(new BigDecimal(1.00));
			ticket.setGroupName("public");
		}
		
    	
    	//model.put("games", lottoGameManager.getAll());
    	model.put("lotteryTicket", ticket);
    	
    	//setModel(model, session.getReferralCode());
   	  
    	List list = new ArrayList<Object>();
    	list.addAll(model.values());
    	try {
    		System.out.println(model.values());
    		return model;
    	}catch(Exception e){
    		System.out.println(model.toString());
    		e.printStackTrace();
    		list.add(e.getMessage());
    	}
    	
    	list.add("hello");
    	
    	return null;
    	
    	//return list;
    }
    
     
}
