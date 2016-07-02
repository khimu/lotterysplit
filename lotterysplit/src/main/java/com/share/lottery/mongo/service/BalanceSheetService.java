package com.share.lottery.mongo.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.share.lottery.model.BalanceSheetDTO;
import com.share.lottery.mongo.manager.MongoManager;

@Component
public class BalanceSheetService implements IBalanceSheetService {
	public final static String ID = "_id";
	
	public final static String USER_ID = "userId";
	public final static String CREATED_ON = "createdOn";
	public final static String LAST_MODIFIED = "lastModified";
	public final static String CURRENT_BALANCE = "currentBalance";
	
	public final static String TOTAL_CREDIT = "totalCredit";
	public final static String TOTAL_DEBIT = "totalDebit";
	
	public final static String DAILY_BALANCE = "dailyBalance";
	public final static String MONTHLY_BALANCE = "monthlyBalance";
	public final static String YEARLY_BALANCE = "yearlyBalance";
	
	public final static String DAILY_CREDIT = "dailyCredit";
	public final static String MONTHLY_CREDIT = "monthlyCredit";
	public final static String YEARLY_CREDIT = "yearlyCredit";
	
	public final static String DAILY_DEBIT = "dailyDebit";
	public final static String MONTHLY_DEBIT = "monthlyDebit";
	public final static String YEARLY_DEBIT = "yearlyDebit";
	
	public final static String DAILY_FEE = "dailyFee";
	public final static String MONTHLY_FEE = "monthlyFee";
	public final static String YEARLY_FEE = "yearlyFee";
	
	public final static String DAILY_TRANS = "dailyTransactions";
	public final static String MONTHLY_TRANS = "monthlyTransactions";
	public final static String YEARLY_TRANS = "yearlyTransactions";
	
	public final static String FEE = "totalFee";
	public final static String TOTAL_TRANSACTIONS = "totalTransactions";

	private final Log log = LogFactory.getLog(getClass());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	@Qualifier("balanceSheetMongoManager")
	private MongoManager balanceSheetMongoManager;
	
	public BalanceSheetDTO fee(BigDecimal fee, Date chargeDate, BalanceSheetDTO sheet) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Long timestamp = Long.parseLong(format.format(chargeDate));
 
        String createdOn = new Long(timestamp).toString();

        String year = createdOn.substring(0, 4);
        String month = createdOn.substring(0, 6);
        String day = createdOn.substring(0, 8);
        
        Double yearlyFee = sheet.getYearlyFee().get(year);
        if(yearlyFee == null){
        	sheet.getYearlyFee().put(year, new Double(fee.doubleValue()));
        }else{
        	sheet.getYearlyFee().put(year, new Double(new BigDecimal(yearlyFee).add(fee).doubleValue()));
        }

        Double monthlyFee = sheet.getMonthlyFee().get(month);
        if(monthlyFee == null){
        	sheet.getMonthlyFee().put(month, new Double(fee.doubleValue()));
        }else{
        	sheet.getMonthlyFee().put(month, new Double(new BigDecimal(monthlyFee).add(fee).doubleValue()));
        }
        
        Double dailyFee = sheet.getDailyFee().get(day);
        if(dailyFee == null){
        	sheet.getDailyFee().put(day, new Double(fee.doubleValue()));
        }else{
        	sheet.getDailyFee().put(day, new Double(new BigDecimal(dailyFee).add(fee).doubleValue()));
        }     

        Integer yearlyTransaction = sheet.getYearlyTransactions().get(day);
        if(yearlyTransaction == null){
        	sheet.getYearlyTransactions().put(day, 1);
        }else{
        	sheet.getYearlyTransactions().put(day, yearlyTransaction + 1);
        }   

        Integer monthlyTransaction = sheet.getMonthlyTransactions().get(day);
        if(monthlyTransaction == null){
        	sheet.getMonthlyTransactions().put(day, 1);
        }else{
        	sheet.getMonthlyTransactions().put(day, monthlyTransaction + 1);
        }   
        
        Integer dailyTransaction = sheet.getDailyTransactions().get(day);
        if(dailyTransaction == null){
        	sheet.getDailyTransactions().put(day, 1);
        }else{
        	sheet.getDailyTransactions().put(day, dailyTransaction + 1);
        }     

		sheet.setCurrentBalance(sheet.getCurrentBalance().subtract(fee));
		sheet.setTotalTransactions(sheet.getTotalTransactions() + 1);
		sheet.setTotalFee(sheet.getTotalFee().add(fee));

		return sheet;
	}
	
	public BalanceSheetDTO credit(BigDecimal amount, Date creditDate, BalanceSheetDTO sheet) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Long timestamp = Long.parseLong(format.format(creditDate));
 
        String createdOn = new Long(timestamp).toString();

        String year = createdOn.substring(0, 4);
        String month = createdOn.substring(0, 6);
        String day = createdOn.substring(0, 8);
        
        Double yearlyBalance = sheet.getYearlyBalance().get(year);
        if(yearlyBalance == null){
        	sheet.getYearlyBalance().put(year, new Double(amount.doubleValue()));
        }else{
        	sheet.getYearlyBalance().put(year, new Double(new BigDecimal(yearlyBalance).add(amount).doubleValue()));
        }

        Double monthlyBalance = sheet.getMonthlyBalance().get(month);
        if(monthlyBalance == null){
        	sheet.getMonthlyBalance().put(month, new Double(amount.doubleValue()));
        }else{
        	sheet.getMonthlyBalance().put(month, new Double(new BigDecimal(monthlyBalance).add(amount).doubleValue()));
        }
        
        Double dayBalance = sheet.getDailyBalance().get(day);
        if(dayBalance == null){
        	sheet.getDailyBalance().put(day, new Double(amount.doubleValue()));
        }else{
        	sheet.getDailyBalance().put(day, new Double(new BigDecimal(dayBalance).add(amount).doubleValue()));
        }       

		sheet.setCurrentBalance(sheet.getCurrentBalance().add(amount));
		sheet.setTotalCredit(sheet.getTotalCredit().add(amount));
		
		return sheet;
	}
	
	public BalanceSheetDTO debit(BigDecimal amount, Date debitDate, BalanceSheetDTO sheet) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Long timestamp = Long.parseLong(format.format(debitDate));
 
        String createdOn = new Long(timestamp).toString();

        String year = createdOn.substring(0, 4);
        String month = createdOn.substring(0, 6);
        String day = createdOn.substring(0, 8);
        
        Double yearlyBalance = sheet.getYearlyBalance().get(year);
        if(yearlyBalance == null){
        	sheet.getYearlyBalance().put(year, new Double(new BigDecimal(0).subtract(amount).doubleValue()));
        }else{
        	sheet.getYearlyBalance().put(year, new Double(new BigDecimal(yearlyBalance).subtract(amount).doubleValue()));
        }

        Double monthlyBalance = sheet.getMonthlyBalance().get(month);
        if(monthlyBalance == null){
        	sheet.getMonthlyBalance().put(month, new Double(new BigDecimal(0).subtract(amount).doubleValue()));
        }else{
        	sheet.getMonthlyBalance().put(month, new Double(new BigDecimal(monthlyBalance).subtract(amount).doubleValue()));
        }
        
        Double dayBalance = sheet.getDailyBalance().get(day);
        if(dayBalance == null){
        	sheet.getDailyBalance().put(day, new Double(new BigDecimal(0).subtract(amount).doubleValue()));
        }else{
        	sheet.getDailyBalance().put(day, new Double(new BigDecimal(dayBalance).subtract(amount).doubleValue()));
        }       

		sheet.setCurrentBalance(sheet.getCurrentBalance().subtract(amount));
		sheet.setTotalDebit(sheet.getTotalDebit().subtract(amount));

		return sheet;
	}	
	
	public int saveBalanceSheet(BalanceSheetDTO sheet){
		// insert
		DBObject dbObject = new BasicDBObject();
		dbObject.put(USER_ID, sheet.getUserId());
		dbObject.put(LAST_MODIFIED, new Date());

		dbObject.put(CURRENT_BALANCE, sheet.getCurrentBalance().doubleValue());
		dbObject.put(TOTAL_DEBIT, sheet.getTotalDebit().doubleValue());
		
		
		dbObject.put(DAILY_BALANCE, sheet.getDailyBalance());
		dbObject.put(MONTHLY_BALANCE, sheet.getMonthlyBalance());
		dbObject.put(YEARLY_BALANCE, sheet.getYearlyBalance());
		
		dbObject.put(DAILY_DEBIT, sheet.getDailyDebit());
		dbObject.put(MONTHLY_DEBIT, sheet.getMonthlyDebit());
		dbObject.put(YEARLY_DEBIT, sheet.getYearlyDebit());


		/*
		 * Credit
		 */

		dbObject.put(TOTAL_CREDIT, sheet.getTotalCredit().doubleValue());
		
		dbObject.put(DAILY_BALANCE, sheet.getDailyBalance());
		dbObject.put(MONTHLY_BALANCE, sheet.getMonthlyBalance());
		dbObject.put(YEARLY_BALANCE, sheet.getYearlyBalance());
		
		dbObject.put(DAILY_CREDIT, sheet.getDailyCredit());
		dbObject.put(MONTHLY_CREDIT, sheet.getMonthlyCredit());
		dbObject.put(YEARLY_CREDIT, sheet.getYearlyCredit());

		/*
		 * Fee
		 */
		
		dbObject.put(TOTAL_TRANSACTIONS, sheet.getTotalTransactions());
		dbObject.put(FEE, sheet.getTotalFee().doubleValue());

		dbObject.put(DAILY_FEE, sheet.getDailyFee());
		dbObject.put(MONTHLY_FEE, sheet.getMonthlyFee());
		dbObject.put(YEARLY_FEE, sheet.getYearlyFee());
		
		dbObject.put(DAILY_TRANS, sheet.getDailyTransactions());
		dbObject.put(MONTHLY_TRANS, sheet.getMonthlyTransactions());
		dbObject.put(YEARLY_TRANS, sheet.getYearlyTransactions());

		
		DBObject delete = new BasicDBObject();
		delete.put(USER_ID, sheet.getUserId());
		balanceSheetMongoManager.remove(delete);

		balanceSheetMongoManager.insert(dbObject);
		return 1;
	}

	public BalanceSheetDTO getBalanceSheet(Long userId) {
		DBCursor cursor = balanceSheetMongoManager.readDataDbCursor(USER_ID, userId);
		if(cursor.hasNext()) {
			cursor.next();

			BalanceSheetDTO sheet = new BalanceSheetDTO();
			sheet.set_id(cursor.curr().get(ID).toString());
			sheet.setUserId((Long) cursor.curr().get(USER_ID));
			
			sheet.setCurrentBalance(new BigDecimal((Double)cursor.curr().get(CURRENT_BALANCE)));

			sheet.setTotalFee(new BigDecimal((Double)cursor.curr().get(FEE)));
			sheet.setTotalTransactions((Integer) cursor.curr().get(TOTAL_TRANSACTIONS));
			
			sheet.setTotalCredit(new BigDecimal((Double)cursor.curr().get(TOTAL_CREDIT)));
			sheet.setTotalDebit(new BigDecimal((Double)cursor.curr().get(TOTAL_DEBIT)));
			
			sheet.setDailyBalance((HashMap<String, Double>) cursor.curr().get(DAILY_BALANCE));
			sheet.setMonthlyBalance((HashMap<String, Double>) cursor.curr().get(MONTHLY_BALANCE));
			sheet.setYearlyBalance((HashMap<String, Double>) cursor.curr().get(YEARLY_BALANCE));

			sheet.setDailyCredit((HashMap<String, Double>) cursor.curr().get(DAILY_CREDIT));
			sheet.setMonthlyCredit((HashMap<String, Double>) cursor.curr().get(MONTHLY_CREDIT));
			sheet.setYearlyCredit((HashMap<String, Double>) cursor.curr().get(YEARLY_CREDIT));
			
			sheet.setDailyDebit((HashMap<String, Double>) cursor.curr().get(DAILY_DEBIT));
			sheet.setMonthlyDebit((HashMap<String, Double>) cursor.curr().get(MONTHLY_DEBIT));
			sheet.setYearlyDebit((HashMap<String, Double>) cursor.curr().get(YEARLY_DEBIT));
			
			sheet.setDailyFee((HashMap<String, Double>) cursor.curr().get(DAILY_FEE));
			sheet.setMonthlyFee((HashMap<String, Double>) cursor.curr().get(MONTHLY_FEE));
			sheet.setYearlyFee((HashMap<String, Double>) cursor.curr().get(YEARLY_FEE));
			
			sheet.setDailyTransactions((HashMap<String, Integer>) cursor.curr().get(DAILY_TRANS));
			sheet.setMonthlyTransactions((HashMap<String, Integer>) cursor.curr().get(MONTHLY_TRANS));
			sheet.setYearlyTransactions((HashMap<String, Integer>) cursor.curr().get(YEARLY_TRANS));
			
			sheet.setCreatedOn((Date) cursor.curr().get(CREATED_ON));
			sheet.setLastModified((Date) cursor.curr().get(LAST_MODIFIED));

			return sheet;
		}else{
			// insert

			BalanceSheetDTO sheet = new BalanceSheetDTO();
			sheet.setUserId(userId);
			
			sheet.setCurrentBalance(new BigDecimal(0.00));

			sheet.setTotalFee(new BigDecimal(0.00));
			sheet.setTotalTransactions(0);
			
			sheet.setTotalCredit(new BigDecimal(0.00));
			sheet.setTotalDebit(new BigDecimal(0.00));
			
			sheet.setDailyBalance(new HashMap<String, Double>());
			sheet.setMonthlyBalance(new HashMap<String, Double>());
			sheet.setYearlyBalance(new HashMap<String, Double>());

			sheet.setDailyCredit(new HashMap<String, Double>());
			sheet.setMonthlyCredit(new HashMap<String, Double>());
			sheet.setYearlyCredit(new HashMap<String, Double>());
			
			sheet.setDailyDebit(new HashMap<String, Double>());
			sheet.setMonthlyDebit(new HashMap<String, Double>());
			sheet.setYearlyDebit(new HashMap<String, Double>());
			
			sheet.setDailyFee(new HashMap<String, Double>());
			sheet.setMonthlyFee(new HashMap<String, Double>());
			sheet.setYearlyFee(new HashMap<String, Double>());
			
			sheet.setDailyTransactions(new HashMap<String, Integer>());
			sheet.setMonthlyTransactions(new HashMap<String, Integer>());
			sheet.setYearlyTransactions(new HashMap<String, Integer>());
			
			sheet.setCreatedOn(new Date());

			return sheet;
		}
	}

}
