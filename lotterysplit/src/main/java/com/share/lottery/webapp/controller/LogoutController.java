package com.share.lottery.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.share.lottery.webapp.form.LoginForm;

@Controller
@RequestMapping("logout.htm")
public class LogoutController extends BaseController {

	public LogoutController() {
		super(LogoutController.class);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logoutUser(Map<String, LoginForm> model, HttpServletRequest request) {

		this.setSessionObject(request.getSession(), null);

		//return new ModelAndView(new RedirectView(getLoginUrl().toString()));
		return "redirect:" + request.getContextPath() + "/lottery/login.htm";
	}
}
