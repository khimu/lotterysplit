package com.share.lottery.webapp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.share.lottery.Constants;
import com.share.lottery.model.Balance;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IEmailService;
import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.ITransactionManager;
import com.share.lottery.service.RoleManager;
import com.share.lottery.service.UserExistsException;
import com.share.lottery.service.UserManager;
import com.share.lottery.webapp.util.RequestUtil;

@Controller
public class FacebookController extends BaseController {
	
	@Autowired
	private IEmailService emailService;
	@Autowired
    private RoleManager roleManager;
    
    @Autowired
    private IBalanceManager balanceManager;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private ITransactionManager transactionManager;
    
	public FacebookController() {
		super(FacebookController.class);
	}

	@RequestMapping(value = "/fblogin.htm", method = RequestMethod.POST)
	public void fblogin(@RequestParam("fbId") String fbId, @RequestParam("accessToken") String accessToken, @RequestParam("email") String email, @RequestParam("name") String name, HttpServletResponse response, HttpServletRequest request){
        final Locale locale = request.getLocale();
        
        Map<String, Object> model = new HashMap<String, Object>();
        
        String referredBy = request.getParameter("referredBy");

        // logger.error("DEBUG " + email + " name : " + name);
        User user = userManager.getUserByUsername(email);
        if(user == null){
        	user = new User();
        	
            user.setReferralCode(RandomStringUtils.randomAlphabetic(10));
            user.setReferredBy(referredBy);
        	
            user.setPassword(RandomStringUtils.randomAlphanumeric(3));
            user.setUsername(email );
            user.setEmail(email);
            String[] names = name.split(" ");
            if(names.length > 1){
            	user.setFirstName(names[0]);
            	user.setLastName(names[1]);
            }else{
            	user.setFirstName(name);
            }
            user.setEnabled(true);
            // Set the default user role on this new user
            user.addRole(roleManager.getRole(Constants.USER_ROLE));
            
            user.setAccessToken(accessToken);
            user.setFacebookId(fbId);
            
            try {
                User newUser = this.getUserManager().saveUser(user);
            	Balance balance = new Balance();
            	balance.setAmount(new BigDecimal(0.00));
            	balance.setLastModified(new Date());
                balance.setUserId(newUser.getId());
                
                balanceManager.save(balance);
            } catch (final AccessDeniedException ade) {
                log.warn(ade.getMessage());
                model.put("result", request.getContextPath() + "/lottery/signup.htm");
                this.output(model, response);
                return;
            } catch (final UserExistsException e) {
            	model.put("result", request.getContextPath() + "/lottery/signup.htm");
            	this.output(model, response);
                return;
            }
            
            // unencrypted users password to log in user automatically
            final String password = user.getPassword();

            saveMessage(request, getText("user.registered", user.getUsername(), locale));
            request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
            
            SessionDTO sessionDTO = userManager.authenticate(user.getUsername(), password);
            request.getSession().setAttribute("SESSION_OBJECT", sessionDTO);

            // Send user an e-mail
            if (log.isDebugEnabled()) {
                log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
            }

            // Send an account information e-mail
            message.setSubject(getText("signup.email.subject", locale));

            try {
            	emailService.save(Constants.EMAIL_OPT_IN, user.getEmail());
                sendUserMessage(user, getText("signup.email.message", locale) + "\n Password: " + password + "\n ReferralLink: http://www.splitlottery.com/lottery/signup.htm?referralCode=" + user.getReferralCode(), RequestUtil.getAppURL(request));
            } catch (final MailException me) {
                saveError(request, me.getMostSpecificCause().getMessage());
            }            
            
            model.put("result", request.getContextPath() + "/lottery/landing.htm");
            this.output(model, response);         
        }else{
            if(StringUtils.isBlank(user.getFacebookId()) == true){
                user.setAccessToken(accessToken);
                user.setFacebookId(fbId);
	            try{
	            	this.getUserManager().saveUser(user);
	            	
	                SessionDTO sessionDTO = userManager.authenticate(user.getUsername());
	                request.getSession().setAttribute("SESSION_OBJECT", sessionDTO);
	                
	                message.setSubject(getText("signup.email.subject", locale));
	
	                try {
	                	emailService.save(Constants.EMAIL_OPT_IN, user.getEmail());
	                    sendUserMessage(user, getText("signup.email.message", locale) + "\n ReferralLink: http://www.splitlottery.com/lottery/signup.htm?referralCode=" + user.getReferralCode(), RequestUtil.getAppURL(request));
	                } catch (final MailException me) {
	                    saveError(request, me.getMostSpecificCause().getMessage());
	                }   
		        } catch (final AccessDeniedException ade) {
		            log.warn(ade.getMessage());
		            model.put("result", request.getContextPath() + "/lottery/signup.htm");
		            this.output(model, response);
		            return;
		        } catch (final UserExistsException e) {
		        	model.put("result", request.getContextPath() + "/lottery/signup.htm");
		        	this.output(model, response);
		        	return;
		        }
            }else{
            	SessionDTO sessionDTO = userManager.authenticate(user.getUsername());
            	request.getSession().setAttribute("SESSION_OBJECT", sessionDTO);
            }
            
            model.put("result", request.getContextPath() + "/lottery/landing.htm");
            this.output(model, response);
        }
	}
	
	@RequestMapping(value = "/fblike.htm", method = RequestMethod.POST)
	public void fblike(@RequestParam("fbId") String fbId, @RequestParam("accessToken") String accessToken, @RequestParam("email") String email, @RequestParam("name") String name, HttpServletResponse response, HttpServletRequest request){
		fblogin(fbId, accessToken, email, name, response, request);
		SessionDTO sessionDTO = this.getSessionObject(request.getSession());
		if(sessionDTO != null) {
			logger.error("session not null");
			transactionManager.fbLikeReward(sessionDTO.getUserId());
			User user = userManager.get(sessionDTO.getUserId());
			if(user.getFbLiked() == false){
				logger.error("fbliked is still false");
			}
		}
	}
}
