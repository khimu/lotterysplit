package com.share.lottery.mobile.webapp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.lottery.Constants;
import com.share.lottery.model.Balance;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.model.UserDTO;
import com.share.lottery.mongo.service.IEmailService;
import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.RoleManager;
import com.share.lottery.service.UserExistsException;
import com.share.lottery.webapp.controller.BaseFormController;
import com.share.lottery.webapp.util.RequestUtil;

@Controller
@RequestMapping("/signup")
public class MobileSignupController extends BaseFormController {
	
    private RoleManager roleManager;
    
    @Autowired
    private IEmailService emailService;
    
    @Autowired
    private IBalanceManager balanceManager;
    
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public MobileSignupController() {
    }

    @RequestMapping(method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody HttpStatus onSubmit(@RequestBody UserDTO userDto, BindingResult errors, final HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	User user = new User();
    	user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        
        String referredBy = request.getParameter("referredBy");
        user.setReferralCode(RandomStringUtils.randomAlphabetic(10));
        user.setReferredBy(referredBy);

        System.out.println(user.toString());

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (StringUtils.isBlank(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }

            if (errors.hasErrors()) {
            	System.out.println(errors.toString());
                return HttpStatus.BAD_REQUEST;
            }
        }

        final Locale locale = request.getLocale();

        user.setEnabled(true);
        
        String isBusiness = request.getParameter("business");
        
        if(StringUtils.isNotEmpty(isBusiness)){
            // Set the default user role on this new user
            user.addRole(roleManager.getRole(Constants.ROLE_CAMPAIGN));
        }else{
            // Set the default user role on this new user
            user.addRole(roleManager.getRole(Constants.USER_ROLE));
        }

        // unencrypted users password to log in user automatically
        final String password = user.getPassword();

        try {
            User newUser = this.getUserManager().saveUser(user);
        	Balance balance = new Balance();
        	balance.setAmount(new BigDecimal(0.00));
        	balance.setLastModified(new Date());
            balance.setUserId(newUser.getId());
            
            balanceManager.save(balance);
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return HttpStatus.FORBIDDEN;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");

            return HttpStatus.FOUND;
        }

        saveMessage(request, getText("user.registered", user.getUsername(), locale));
        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        SessionDTO sessionDTO = userManager.authenticate(user.getUsername(), password);
        request.getSession().setAttribute("SESSION_OBJECT", sessionDTO);

        /*
        // log user in automatically
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), password, user.getAuthorities());
        
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
        */

        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
        }
        
        final User tmpUser = user;

        Thread t = new Thread(new Runnable (){
			@Override
			public void run() {
		        // Send an account information e-mail
		        message.setSubject(getText("signup.email.subject", locale));

		        try {
		        	emailService.save(Constants.EMAIL_OPT_IN, tmpUser.getEmail());
		            sendUserMessage(tmpUser, getText("signup.email.message", locale) + "\n ReferralLink: http://www.splitlottery.com/lottery/signup.htm?referralCode=" + tmpUser.getReferralCode(), RequestUtil.getAppURL(request));
		        } catch (final MailException me) {
		            //saveError(request, me.getMostSpecificCause().getMessage());
		        }
			}
        });
        
        t.start();

		return HttpStatus.CREATED;
    }
}
