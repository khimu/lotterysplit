package com.share.lottery.webapp.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.authorize.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.model.CampaignDTO;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.mongo.service.ICampaignService;
import com.share.lottery.service.UserManager;

@Controller
@RequestMapping("/campaign")
public class CampaignController extends BaseController {

		@Autowired
		private ICampaignService campaignService;
		
		@Autowired
		private UserManager userManager;
		
		public CampaignController() {
			super(CampaignController.class);
		}

	    @RequestMapping(value="/list.htm", method = RequestMethod.GET)
	    public ModelAndView list(HttpServletRequest request) throws Exception {
			SessionDTO session = this.getSessionObject(request.getSession());
			if(session == null || session.isCampaigner() == false){
				return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
			}

			List<CampaignDTO> campaigns = campaignService.getCampaigns(session.getUserId());
			
	    	Map<String, Object> model = new HashMap<String, Object>();

	    	model.put("campaigns", campaigns);
	    	setModel(model, session.getReferralCode());
	        return new ModelAndView("campaign/list", model);
	    }
	    
	    @RequestMapping(value="/create.htm", method = RequestMethod.GET)
	    public ModelAndView create(HttpServletRequest request) throws Exception {
			SessionDTO session = this.getSessionObject(request.getSession());
			if(session == null || session.isCampaigner() == false){
				return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
			}
			
			String lotteryTicketId = request.getParameter("lotteryTicketId");
			if(StringUtils.isEmpty(lotteryTicketId)){
				return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/ticket/split.htm");
			}

	    	Map<String, Object> model = new HashMap<String, Object>();
	    	CampaignDTO campaign = new CampaignDTO();
	    	campaign.setUserId(session.getUserId());
	    	campaign.setLotteryTicketId(Long.parseLong(lotteryTicketId));

	    	model.put("campaign", campaign);
	    	setModel(model, session.getReferralCode());
	        return new ModelAndView("campaign/create", model);
	    }	    
	    
	    @RequestMapping(value="/create.htm", method = RequestMethod.POST)
	    public ModelAndView submitCampaign(@ModelAttribute("campaign") CampaignDTO campaign, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {
			SessionDTO session = this.getSessionObject(request.getSession());
			if(session == null || session.isCampaigner() == false){
				return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
			}
			
			campaignService.saveCampaign(session.getUserId(), campaign.getCampaignName(), campaign.getBrand(), campaign.getLotteryTicketId());

			return new ModelAndView("redirect:/lottery/campaign/list.htm", model);
	    }
	    
	    @RequestMapping(value="/pool.htm", method = {RequestMethod.GET, RequestMethod.POST})
	    public void updatePool(@RequestParam("campaignName") String campaignName, @RequestParam("email") String email, @RequestParam("fbId") String fbId, HttpServletRequest request, HttpServletResponse response) throws Exception {
			campaignService.updateCampaign(campaignName, email, fbId);

			response.setContentType("text/html;charset=UTF-8");
	        response.addHeader("Access-Control-Allow-Origin", "*");
	        PrintWriter out = response.getWriter();
	        try {
	            out.println("'success'");
	        } catch (Exception e) {
	            out.println("'error'");
	        } finally {
	            out.flush();
	            out.close();
	        } 
	    }
}
