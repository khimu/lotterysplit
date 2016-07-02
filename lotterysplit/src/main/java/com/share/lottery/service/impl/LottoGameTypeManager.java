package com.share.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.ILottoGameTypeDao;
import com.share.lottery.model.LottoGameType;
import com.share.lottery.service.ILottoGameTypeManager;

@Transactional("lotterySplitTransactionManager")
@Service("lottoGameTypeManager")
public class LottoGameTypeManager  extends GenericManagerImpl<LottoGameType, Long> implements ILottoGameTypeManager {

	private ILottoGameTypeDao lottoGameTypeDao;
	
	@Autowired
	public LottoGameTypeManager(ILottoGameTypeDao lottoGameTypeDao){
		super(lottoGameTypeDao);
		this.lottoGameTypeDao = lottoGameTypeDao;
	}

}
