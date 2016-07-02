package com.share.lottery.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.service.IPaymentManager;
import com.share.lottery.service.ITransactionManager;
import com.share.lottery.service.UserManager;

@Controller
public class PaypalController extends BaseController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ITransactionManager transactionManager;
	
	@Autowired
	private IPaymentManager paymentManager;

	public PaypalController() {
		super(PaypalController.class);
	}

	@RequestMapping(value="/paypal/payment", method = RequestMethod.GET)
	public String makePayment(HttpServletRequest request, Map<String, Object> model){
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		
		String paypalAmount =  request.getParameter("paypalAmount"); 
		if(paypalAmount == null){
			paypalAmount = "5";
		}
		
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "live");

		String accessToken = null;
		try {
			accessToken = new OAuthTokenCredential("Adc-_hDToF5hdAVZqkjs2KoiaP9rkM4jrrNt1FZlP6C2m8s691lzJCEvT79Q", "EEdZ4RBK7UfMQgdElYhumZ_zPtXD6azkYw6BL4sYXbozcx2fe7dOry73YzGI", sdkConfig).getAccessToken();
		} catch (PayPalRESTException e) {
			logger.error(session.getUsername() + " " + e.getMessage());
			model.put("error", e.getMessage());
			return "payment/paypalError";
		}

		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(paypalAmount);

		Transaction transaction = new Transaction();
		transaction.setDescription("Split Lottery $5 Fund");
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		User user = userManager.get(session.getUserId());

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://www.splitlottery.com/lottery/payment/form.htm");
		
		try {
			redirectUrls.setReturnUrl("http://www.splitlottery.com/lottery/paypal/process/" + URLEncoder.encode(user.getEmail(), "UTF-8"));
			payment.setRedirectUrls(redirectUrls);

			try {
				Payment createdPayment = payment.create(apiContext);

				Iterator<Links> links = createdPayment.getLinks().iterator();
				while(links.hasNext()){
					Links link = links.next();
					if("approval_url".equals(link.getRel())){
						return "redirect:" + link.getHref();
					}
				}
			} catch (PayPalRESTException e) {
				logger.error(session.getUsername() + " " + e.getMessage());
				model.put("error", e.getMessage());
				return "payment/paypalError";
			}
			
			return "redirect" + redirectUrls.getCancelUrl();
		} catch (UnsupportedEncodingException e1) {
			logger.error(session.getUsername() + " " + e1.getMessage());
			model.put("error", e1.getMessage());
			return "payment/paypalError";
		}
	}
	
	@RequestMapping(value="/paypal/process/{email}", method = RequestMethod.GET)
	public String processPayment(@PathVariable("email") String email,HttpServletRequest request, Map<String, Object> model){		
		String payerId = request.getParameter("PayerID");
		String token = request.getParameter("token");
		
		User user;
		try {
			user = userManager.getUserByUsername(URLDecoder.decode(email.toLowerCase(), "UTF-8") + ".com");
			com.share.lottery.model.Payment payment = new com.share.lottery.model.Payment(user.getId(), payerId, new BigDecimal(5), token, new Date(), 1, "");
			
			transactionManager.payment(payment, user.getId());
			
			return "redirect:" + request.getContextPath() + "/lottery/report/balance.htm";
		} catch (UsernameNotFoundException e) {
			logger.error(email + " " + e.getMessage());
			model.put("error", e.getMessage());
			return "payment/paypalError";
		} catch (UnsupportedEncodingException e) {
			logger.error(email + " " + e.getMessage());
			model.put("error", e.getMessage());
			return "payment/paypalError";
		}

	}

}
