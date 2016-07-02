package com.share.lottery.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lottery.model.LottoGame;
import com.share.lottery.service.ILottoGameManager;

@Controller
public class WinningLotteryController extends BaseController {
	
	@Autowired
	private ILottoGameManager lottoGameManager;
	
	private String[] names = {"powerball", "mega millions"};
	
	private String[] links = {"http://www.powerball.com/powerball/pb_numbers.asp", "http://www.powerball.com/megamillions/mm_numbers.asp"};

	public WinningLotteryController(){
		super(WinningLotteryController.class);
	}
	
    @RequestMapping(value="/update.htm", method = RequestMethod.GET)
	public void update(HttpServletResponse response)  throws Exception{
    	parsePowerBall();
    	parseMegaMillions();
    	
        PrintWriter out = response.getWriter();

        try {       
            out.println("('success')");
        } catch (Exception e) {
            out.println("('error')");
        } finally {
            out.flush();
            out.close();
        }
	}
    
    @RequestMapping(value="/powerball.htm", method = RequestMethod.GET)
	public ModelAndView powerball(HttpServletResponse response) throws Exception{
    	Map<String, Object> model = new HashMap<String, Object>();
    	LottoGame game = lottoGameManager.get(1L);
    	model.put("game", game);
    	return new ModelAndView("ajax/winning", model);
	}
    
    @RequestMapping(value="/megamillion.htm", method = RequestMethod.GET)
	public ModelAndView megaMillion(HttpServletResponse response) throws Exception{
    	Map<String, Object> model = new HashMap<String, Object>();
    	LottoGame game = lottoGameManager.get(2L);
    	model.put("game", game);
    	return new ModelAndView("ajax/winning", model);
	}
    
    public void parsePowerBall(){
		try {
			Document doc = Jsoup.connect(links[0]).get();
			Elements titles = doc.select("b");
			Elements winning = doc.select("strong");
			String winningAmount = "$" + winning.get(0).html().replaceAll("&nbsp;&nbsp;\\$", "").trim();

			String drawDate = titles.get(3).html();

			StringBuilder builder = new StringBuilder();
			
			for(int i = 4; i < 10; i ++){
				builder.append(titles.get(i).child(0).html());
				builder.append(" ");
			}
			System.out.println("updating " + names[0] + " " + winningAmount);
			lottoGameManager.updateDrawing(names[0], winningAmount.trim(), builder.toString().trim(), drawDate.trim(), "");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    }
    
    public void parseMegaMillions(){
		try {
			Document doc = Jsoup.connect(links[1]).get();
			Elements titles = doc.select("b");
			Elements winning = doc.select("strong");
			String winningAmount = "$" + winning.get(0).html().replaceAll("&nbsp;&nbsp;\\$", "").trim();

			String drawDate = titles.get(2).html();

			StringBuilder builder = new StringBuilder();
			
			for(int i = 3; i < 9; i ++){
				builder.append(titles.get(i).child(0).html());
				builder.append(" ");
			}
			
			System.out.println("updating " + names[1] + " " + winningAmount);
			lottoGameManager.updateDrawing(names[1], winningAmount.trim(), builder.toString().trim(), drawDate.trim(), "");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}
