package com.share.lottery.type;

public final class LottoGameFactory {

	private LottoGameFactory(){
		
	}
	
	public LottoGameRuleEngine getGame(String className){
		if(className.equals(PowerBallLotto.class.getSimpleName())){
			return new PowerBallLotto();
		}
		return null;
	}
	
}
