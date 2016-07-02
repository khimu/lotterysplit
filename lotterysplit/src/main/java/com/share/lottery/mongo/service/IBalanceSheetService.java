package com.share.lottery.mongo.service;

import java.math.BigDecimal;
import java.util.Date;

import com.share.lottery.model.BalanceSheetDTO;

public interface IBalanceSheetService {
	
	public BalanceSheetDTO fee(BigDecimal fee, Date chargeDate, BalanceSheetDTO sheet);

	public BalanceSheetDTO credit(BigDecimal amount, Date creditDate, BalanceSheetDTO sheet);
	
	public BalanceSheetDTO debit(BigDecimal amount, Date debitDate, BalanceSheetDTO sheet);
	
	public int saveBalanceSheet(BalanceSheetDTO sheet);

	public BalanceSheetDTO getBalanceSheet(Long userId);
}
