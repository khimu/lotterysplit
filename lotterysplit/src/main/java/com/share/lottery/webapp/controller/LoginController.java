package com.share.lottery.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.model.SessionDTO;
import com.share.lottery.service.IBalanceManager;
import com.share.lottery.service.ILotteryTicketManager;
import com.share.lottery.service.UserManager;
import com.share.lottery.webapp.form.LoginForm;

@Controller
@RequestMapping("login.htm")
public class LoginController extends BaseController {
	private final static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private IBalanceManager balanceService;
	
	@Autowired
	private ILotteryTicketManager lotteryTicketService;

	public LoginController() {
		super(LoginController.class);
	}

	/**
	 * Process show form request.
	 * 
	 * @param model
	 *            the model
	 * @return the model and view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView processShowFormRequest(HttpServletRequest request, Map<String, Object> model) {
		LoginForm loginForm = new LoginForm();
		model.put("loginForm", loginForm);
		model.put("ticket", lotteryTicketService.getRandomFreeLottery());
		setModel(model);
		if(this.getSessionObject(request.getSession()) != null) {
			logger.debug("Already logged in " + this.getSessionObject(request.getSession()).getUsername());
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/landing.htm");
		}
		return new ModelAndView("loginForm", model);
	}

	/**
	 * Process form.
	 *
	 * @param loginForm the login form
	 * @param result the result
	 * @param model the model
	 * @return the model and view
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processForm(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		logger.debug("Logging in processForm");
		model.put("loginForm", loginForm);
		setModel(model);

		if (!result.hasErrors()) {
			try {
				SessionDTO session = userManager.authenticate(loginForm.getUsername(), loginForm.getPassword());
				if(session == null){
					logger.debug("session is null after authenticate for username  " + loginForm.getUsername());
					return new ModelAndView("loginForm", model);
				}
				this.setSessionObject(request.getSession(), session);
				
				logger.debug("Session is not null for " + loginForm.getUsername() + " redirecting to /lottery/landing.htm");
				return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/landing.htm");
			}catch(Exception e){
				model.put("error", "You do not have access.  Please register an account");
				return new ModelAndView("loginForm", model);
			}

		}
		
		return new ModelAndView("loginForm", model);
	}

}
