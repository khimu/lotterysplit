package com.share.lottery.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.authorize.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IGroupLotteryService;
import com.share.lottery.service.UserManager;
import com.share.lottery.util.EMailUtil;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

	@Autowired
	private IGroupLotteryService groupLotteryService;
	
	@Autowired
	private UserManager userManager;
	
	public GroupController() {
		super(GroupController.class);
	}

    @RequestMapping(value="/newgroup.htm", method = RequestMethod.GET)
    public ModelAndView newgroup(HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		String action = request.getParameter("action");
		
    	Map<String, Object> model = new HashMap<String, Object>();
    	GroupForm groupForm = new GroupForm();
    	groupForm.setGroupName("public");
    	groupForm.setAction(action);
    	model.put("groupForm", groupForm);
    	setModel(model, session.getReferralCode());
        return new ModelAndView("ticket/group", model);
    }
    
    @RequestMapping(value="/newgroup.htm", method = RequestMethod.POST)
    public ModelAndView submitGroupName(@ModelAttribute("groupForm") GroupForm groupForm, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		// if group exist, user must exist to create ticket in group

		// if group does not exist, create group
		
		// if group exist, user does not exist - do nothing
		
		User user = userManager.get(session.getUserId());
		
		String groupName = groupForm.getGroupName();
		if(StringUtils.isNotEmpty(groupForm.getMemberEmails()) && StringUtils.isNotEmpty(groupName)){
			if(groupLotteryService.groupNameAlreadyExist(groupName)){
				// allow user to invite to existing group
				if(groupLotteryService.userExistInGroup(session.getUserId(), groupName) == true || groupLotteryService.userExistInGroup(session.getUsername(), groupName)){
					groupLotteryService.saveOrUpdateGroupMembers(groupName, groupForm.getMemberEmails().split(","));
				}
			}else{
				// create group and invite
				List<Long> userIds = new ArrayList<Long>();					
				userIds.add(session.getUserId());
				groupLotteryService.saveOrUpdateGroupMembers(groupName, groupForm.getMemberEmails().split(","), userIds);
			}
		}else if(StringUtils.isNotEmpty(groupName)){
			// create a new group with user being first member
			if(groupLotteryService.groupNameAlreadyExist(groupName) == false){
				groupLotteryService.saveOrUpdateGroupMember(groupName, session.getUserId());
			}
		}
		
		// i would send this into a MQ and process the emails there to free up this thread
		if(StringUtils.isNotEmpty(groupForm.getMemberEmails())){
			message.setSubject("Your friend " + user.getFirstName() + " has invited you to join his lottery pool");

			String[] emails = groupForm.getMemberEmails().split(",");
			for(String email : emails){
				EMailUtil.inviteToPool(user, templateName, message, mailEngine, email, "Please join my lottery pool (" + groupName + ") at http://www.splitlottery.com");
			}
		}
		
    	model.put("groupName", groupName);
    	setModel(model, session.getReferralCode());
    	if(groupForm.getAction().equals("list")){
    		return new ModelAndView("redirect:/lottery/ticket/split.htm?groupName="+groupName, model);
    	}else{
    		return new ModelAndView("redirect:/lottery/ticket/order.htm?groupName="+groupName, model);
    	}
    }
}
