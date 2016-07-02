package com.share.lottery.dao;

import com.share.lottery.model.LottoGame;

public interface ILottoGameDao extends GenericDao<LottoGame, Long> {
	
	public void updateDrawing(String name, String winningAmount, String numbers, String prevDrawingDate, String nextDrawingDate);

}
