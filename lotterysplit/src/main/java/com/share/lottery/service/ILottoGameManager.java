package com.share.lottery.service;

import java.util.Map;

import com.share.lottery.model.LottoGame;

public interface ILottoGameManager extends GenericManager<LottoGame, Long> {

	public void getLandingPageData(Map<String, Object> model);
	
	public void updateDrawing(String name, String winningAmount, String numbers, String prevDrawingDate, String nextDrawingDate);
	
}
