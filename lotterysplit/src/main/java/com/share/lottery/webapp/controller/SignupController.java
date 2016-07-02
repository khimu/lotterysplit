package com.share.lottery.webapp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.share.lottery.Constants;
import com.share.lottery.model.Balance;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IEmailService;
import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.RoleManager;
import com.share.lottery.service.UserExistsException;
import com.share.lottery.webapp.util.RequestUtil;

/**
 * Controller to signup new users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/signup.htm")
public class SignupController extends BaseFormController {
    private RoleManager roleManager;
    
    @Autowired
    private IEmailService emailService;
    
    @Autowired
    private IBalanceManager balanceManager;
    
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public SignupController() {
        setCancelView("redirect:/lottery/login.htm");
        setSuccessView("redirect:/lottery/landing.htm");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    public User showForm() {
        return new User();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final User user, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
        if (request.getParameter("cancel") != null) {
            return "redirect:" + request.getContextPath() + "/lottery/login.htm";
        }
        
        String referredBy = request.getParameter("referredBy");
        user.setReferralCode(RandomStringUtils.randomAlphabetic(10));
        user.setReferredBy(referredBy);
        user.setUsername(user.getEmail());

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (StringUtils.isBlank(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }

            if (errors.hasErrors()) {
                return "signup";
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
            return null;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");

            return "signup";
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

        Thread t = new Thread(new Runnable (){
			@Override
			public void run() {
		        // Send an account information e-mail
		        message.setSubject(getText("signup.email.subject", locale));

		        try {
		        	emailService.save(Constants.EMAIL_OPT_IN, user.getEmail());
		            sendUserMessage(user, getText("signup.email.message", locale) + "\n ReferralLink: http://www.splitlottery.com/lottery/signup.htm?referralCode=" + user.getReferralCode(), RequestUtil.getAppURL(request));
		        } catch (final MailException me) {
		            saveError(request, me.getMostSpecificCause().getMessage());
		        }
			}
        });
        t.start();

		return "redirect:" + request.getContextPath() + "/lottery/landing.htm";
    }
}
