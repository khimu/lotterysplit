package com.share.lottery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BalanceSheetDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _id;

	private Long userId;

	private Map<String, Double> dailyBalance;

	private Map<String, Double> monthlyBalance;

	private Map<String, Double> yearlyBalance;

	private Map<String, Double> dailyCredit;

	private Map<String, Double> monthlyCredit;

	private Map<String, Double> yearlyCredit;

	private Map<String, Double> dailyDebit;

	private Map<String, Double> monthlyDebit;

	private Map<String, Double> yearlyDebit;

	private Map<String, Integer> dailyTransactions;

	private Map<String, Integer> monthlyTransactions;

	private Map<String, Integer> yearlyTransactions;

	private Map<String, Double> dailyFee;

	private Map<String, Double> monthlyFee;

	private Map<String, Double> yearlyFee;

	private BigDecimal currentBalance;

	private Integer totalTransactions;

	private Date lastModified;

	private Date createdOn;

	private BigDecimal totalFee;

	private BigDecimal totalCredit;

	private BigDecimal totalDebit;

	private BigDecimal outstandingBillAmount;

	private BigDecimal lastBilledAmount;
	private Date lastBilled;

	private Date lastCashOut;
	private BigDecimal lastCashOutAmount;

	public BalanceSheetDTO() {
		dailyBalance = new HashMap<String, Double>();
		monthlyBalance = new HashMap<String, Double>();
		yearlyBalance = new HashMap<String, Double>();

		dailyTransactions = new HashMap<String, Integer>();
		monthlyTransactions = new HashMap<String, Integer>();
		yearlyTransactions = new HashMap<String, Integer>();

		dailyFee = new HashMap<String, Double>();
		monthlyFee = new HashMap<String, Double>();
		yearlyFee = new HashMap<String, Double>();

		dailyCredit = new HashMap<String, Double>();
		monthlyCredit = new HashMap<String, Double>();
		yearlyCredit = new HashMap<String, Double>();

		dailyDebit = new HashMap<String, Double>();
		monthlyDebit = new HashMap<String, Double>();
		yearlyDebit = new HashMap<String, Double>();
	}

	public void updateBalance(Date date) {

	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Map<String, Integer> getDailyTransactions() {
		return dailyTransactions;
	}

	public void setDailyTransactions(Map<String, Integer> dailyTransactions) {
		this.dailyTransactions = dailyTransactions;
	}

	public Map<String, Integer> getMonthlyTransactions() {
		return monthlyTransactions;
	}

	public void setMonthlyTransactions(Map<String, Integer> monthlyTransactions) {
		this.monthlyTransactions = monthlyTransactions;
	}

	public Map<String, Integer> getYearlyTransactions() {
		return yearlyTransactions;
	}

	public void setYearlyTransactions(Map<String, Integer> yearlyTransactions) {
		this.yearlyTransactions = yearlyTransactions;
	}

	public Integer getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(Integer totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public Date getLastBilled() {
		return lastBilled;
	}

	public void setLastBilled(Date lastBilled) {
		this.lastBilled = lastBilled;
	}

	public Date getLastCashOut() {
		return lastCashOut;
	}

	public void setLastCashOut(Date lastCashOut) {
		this.lastCashOut = lastCashOut;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}

	public BigDecimal getOutstandingBillAmount() {
		return outstandingBillAmount;
	}

	public void setOutstandingBillAmount(BigDecimal outstandingBillAmount) {
		this.outstandingBillAmount = outstandingBillAmount;
	}

	public BigDecimal getLastBilledAmount() {
		return lastBilledAmount;
	}

	public void setLastBilledAmount(BigDecimal lastBilledAmount) {
		this.lastBilledAmount = lastBilledAmount;
	}

	public BigDecimal getLastCashOutAmount() {
		return lastCashOutAmount;
	}

	public void setLastCashOutAmount(BigDecimal lastCashOutAmount) {
		this.lastCashOutAmount = lastCashOutAmount;
	}

	public Map<String, Double> getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(Map<String, Double> dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	public Map<String, Double> getMonthlyBalance() {
		return monthlyBalance;
	}

	public void setMonthlyBalance(Map<String, Double> monthlyBalance) {
		this.monthlyBalance = monthlyBalance;
	}

	public Map<String, Double> getYearlyBalance() {
		return yearlyBalance;
	}

	public void setYearlyBalance(Map<String, Double> yearlyBalance) {
		this.yearlyBalance = yearlyBalance;
	}

	public Map<String, Double> getDailyCredit() {
		return dailyCredit;
	}

	public void setDailyCredit(Map<String, Double> dailyCredit) {
		this.dailyCredit = dailyCredit;
	}

	public Map<String, Double> getMonthlyCredit() {
		return monthlyCredit;
	}

	public void setMonthlyCredit(Map<String, Double> monthlyCredit) {
		this.monthlyCredit = monthlyCredit;
	}

	public Map<String, Double> getYearlyCredit() {
		return yearlyCredit;
	}

	public void setYearlyCredit(Map<String, Double> yearlyCredit) {
		this.yearlyCredit = yearlyCredit;
	}

	public Map<String, Double> getDailyDebit() {
		return dailyDebit;
	}

	public void setDailyDebit(Map<String, Double> dailyDebit) {
		this.dailyDebit = dailyDebit;
	}

	public Map<String, Double> getMonthlyDebit() {
		return monthlyDebit;
	}

	public void setMonthlyDebit(Map<String, Double> monthlyDebit) {
		this.monthlyDebit = monthlyDebit;
	}

	public Map<String, Double> getYearlyDebit() {
		return yearlyDebit;
	}

	public void setYearlyDebit(Map<String, Double> yearlyDebit) {
		this.yearlyDebit = yearlyDebit;
	}

	public Map<String, Double> getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(Map<String, Double> dailyFee) {
		this.dailyFee = dailyFee;
	}

	public Map<String, Double> getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(Map<String, Double> monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public Map<String, Double> getYearlyFee() {
		return yearlyFee;
	}

	public void setYearlyFee(Map<String, Double> yearlyFee) {
		this.yearlyFee = yearlyFee;
	}

}
