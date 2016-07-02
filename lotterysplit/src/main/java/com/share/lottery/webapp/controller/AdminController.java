package com.share.lottery.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.share.lottery.dao.SearchException;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.LottoGameType;
import com.share.lottery.model.SessionDTO;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IGroupLotteryService;
import com.share.lottery.service.ILotteryTicketManager;
import com.share.lottery.service.ILottoGameManager;
import com.share.lottery.service.ILottoGameTypeManager;
import com.share.lottery.util.BarcodeReaderUtil;
import com.share.lottery.util.EMailUtil;
import com.share.lottery.webapp.form.LottoGameForm;
//import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private ILottoGameManager lottoGameService;
	
	@Autowired
	private ILottoGameManager lottoGameManager;

	@Autowired
	private ILotteryTicketManager lotteryTicketService;
	
	@Autowired
	private ILottoGameTypeManager lottoGameTypeService;
	
	@Autowired
	private IGroupLotteryService groupLotteryService;
	
	public AdminController(){
		super(AdminController.class);
	}
	
    @RequestMapping(value="/list.htm", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "start", required = false, defaultValue = "1") Integer start, HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null || session.isAdmin() == false){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<LotteryTicket> tickets = lotteryTicketService.getAllOrders((start - 1) * 50);
		
		model.put("tickets", tickets);
    	model.put("totalCount", lotteryTicketService.getAllOrderCount());
    	model.put("pageSize", 50);
    	model.put("start", start);
    	
    	setModel(model, session.getReferralCode());
        return new ModelAndView("admin/list", model);
    }

    @RequestMapping(value="/split.htm", method = RequestMethod.GET)
    public ModelAndView getLotteryTicket(HttpServletRequest request) throws Exception {
		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null || session.isAdmin() == false){
			return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/login.htm");
		}
		
    	Long lotteryTicketId = request.getParameter("lotteryTicketId") == null ? null : Long.parseLong(request.getParameter("lotteryTicketId"));
    	Map<String, Object> model = new HashMap<String, Object>();
    	if(lotteryTicketId == null){
    		return new ModelAndView("redirect:" + request.getContextPath() + "/lottery/admin/list.htm?start=1", model);
    	}else{
    		model.put("games", lottoGameManager.getAll());
    		LotteryTicket ticket = lotteryTicketService.get(lotteryTicketId);
    		model.put("lotteryTicket", ticket);
    	}
    	
    	setModel(model, session.getReferralCode());
        return new ModelAndView("admin/split", model);
    }

    @RequestMapping(value="/split.htm", method = RequestMethod.POST)
    public String saveLottoGame( @ModelAttribute("lotteryTicket") LotteryTicket lotteryTicket, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {

		SessionDTO session = this.getSessionObject(request.getSession());
		if(session == null || session.isAdmin() == false){
			return "redirect:" + request.getContextPath() + "/lottery/login.htm";
		}
		        
        if(lotteryTicket.getSplitNumber() > 100){
			model.put("error", "Group size cannot exceed 100");
			model.put("games", lottoGameManager.getAll());
			setModel(model, session.getReferralCode());
			return "ticket/split";
        }
        
        lotteryTicket.setAlreadyPurchased(true);
        
        try {
            try {
    			lotteryTicket.setTicketNumber(BarcodeReaderUtil.read(lotteryTicket.getImageBytes()));
    		} catch (NotFoundException e) {
    			//return Constants.ListLottery.UNSUPPORTED_BARCODE;
    		} catch (ChecksumException e) {
    			//return Constants.ListLottery.UNSUPPORTED_BARCODE;
    		} catch (FormatException e) {
    			//return Constants.ListLottery.UNSUPPORTED_BARCODE;
    		} catch (IOException e) {
    			//return Constants.ListLottery.UNSUPPORTED_BARCODE;
    		}
        	
        	lotteryTicketService.save(lotteryTicket);
    		groupLotteryService.saveOrUpdateGroupTicket(lotteryTicket.getGroupName(), lotteryTicket.getId());
        	User user = userManager.get(session.getUserId());
        	EMailUtil.orderComplete(templateName, message, mailEngine, user, "Lotto Order Complete: Numbers: " + lotteryTicket.getNumbers() + " Draw Date: " + lotteryTicket.getTicketDate() + " Lottery Game : " + lotteryTicket.getLottoGameName());
        }catch(Exception e){
			List<String> errors = new ArrayList<String>();
			errors.add(e.getMessage());
			model.put("errors", errors);
			return "error";
		}

    	return "redirect:" + request.getContextPath() + "/lottery/ticket/detail/" + lotteryTicket.getId();
    }
 
    @RequestMapping(value="/validationengine", method = RequestMethod.POST)
    public String savevalidation(@RequestParam("lottoGameEngineName") String lottoGameEngineName, Map<String, Object> model, HttpServletRequest request) throws Exception {
        try {
        	LottoGameType type = new LottoGameType();
        	type.setClassName(lottoGameEngineName);
        	lottoGameTypeService.save(type);
        } catch (SearchException se) {
            model.put("searchError", se.getMessage());
        }
        return "redirect:/admin/list";
    }

    @RequestMapping(value="/newgame", method = RequestMethod.GET)
    public ModelAndView lottoGame(HttpServletRequest request) throws Exception {
    	Long id = request.getParameter("id") == null ? null : Long.parseLong(request.getParameter("id"));
        Model model = new ExtendedModelMap();
        try {
        	LottoGameForm lottoGame = new LottoGameForm();
        	if(id != null){
        		lottoGame.setLottoGame(lottoGameService.get(id));
        	}
            model.addAttribute("gameTypes", lottoGameTypeService.getAll());
            model.addAttribute("lottoGameForm", lottoGame);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("admin/newgame", model.asMap());
    }
    
    @RequestMapping(value="/newgame", method = RequestMethod.POST)
    public String saveLottoGame( @ModelAttribute("lottoGameForm") LottoGameForm lottoGameForm, BindingResult result, Map<String, Object> model, HttpServletRequest request) throws Exception {
        try {
        	lottoGameService.save(lottoGameForm.getLottoGame());
        } catch (SearchException se) {
            model.put("searchError", se.getMessage());
        }
        return "redirect:/admin/list";
    }
    
}
