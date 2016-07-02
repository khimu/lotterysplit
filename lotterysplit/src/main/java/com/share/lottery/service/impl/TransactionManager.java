package com.share.lottery.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.share.lottery.Constants;
import com.share.lottery.dao.IBalanceDao;
import com.share.lottery.dao.ILotteryBuyerDao;
import com.share.lottery.dao.ILotteryTicketDao;
import com.share.lottery.dao.IPaymentDao;
import com.share.lottery.dao.ITransactionDao;
import com.share.lottery.dao.UserDao;
import com.share.lottery.exceptions.BuyException;
import com.share.lottery.exceptions.ListException;
import com.share.lottery.model.Balance;
import com.share.lottery.model.BalanceSheetDTO;
import com.share.lottery.model.LotteryBuyer;
import com.share.lottery.model.LotteryTicket;
import com.share.lottery.model.Payment;
import com.share.lottery.model.Transaction;
import com.share.lottery.model.User;
import com.share.lottery.mongo.service.IBalanceSheetService;
import com.share.lottery.mongo.service.IGroupLotteryService;
import com.share.lottery.mongo.service.IImageService;
import com.share.lottery.service.ITransactionManager;
import com.share.lottery.service.MailEngine;
import com.share.lottery.util.BarcodeReaderUtil;
import com.share.lottery.util.EMailUtil;

@Transactional("lotterySplitTransactionManager")
@Service("transactionManager")
public class TransactionManager extends GenericManagerImpl<Transaction, Long> implements ITransactionManager {

	Logger logger = Logger.getLogger(TransactionManager.class);
	
	@Autowired
	private IGroupLotteryService groupLotteryService;
	
	private ITransactionDao transactionDao;

	@Autowired
	private ILotteryBuyerDao lotteryBuyerDao;
	
	@Autowired
	private ILotteryTicketDao lotteryTicketDao;

	@Autowired
	private IPaymentDao paymentDao;
	
	@Autowired
	private IBalanceDao balanceDao;
	
	//@Autowired
	//private IImageService imageService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IBalanceSheetService balanceSheetService;
	
    @Autowired
    MailEngine mailEngine;

    @Autowired
    SimpleMailMessage message;
    
    private MessageSourceAccessor messages;
    
    private String templateName = "referralScoreEmail.vm";

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }
	
	public TransactionManager(){}

	@Autowired
    public TransactionManager(@Qualifier("transactionDao") ITransactionDao transactionDao) {
        super(transactionDao);
        this.transactionDao = transactionDao;
    }
	
	@Transactional(rollbackFor = Exception.class, value = "lotterySplitTransactionManager")
	public void fbLikeReward(Long userId){
		User user = userDao.get(userId);
		
		if(user.getFbLiked() == false){
			Balance creditBalance = balanceDao.findBalanceByUserId(userId);
			creditBalance.setLastModified(new Date());
			creditBalance.setAmount(creditBalance.getAmount().add(new BigDecimal(1)).round(MathContext.DECIMAL32));
			balanceDao.save(creditBalance);
	
			Transaction credit = new Transaction();
			credit.setType(Transaction.PAYMENT);
			credit.setUserId(userId);
			credit.setAmount(new BigDecimal(1));
			credit.setCreatedOn(new Date());
			credit.setCurrentBalance(creditBalance.getAmount());
			transactionDao.save(credit);
	
			BalanceSheetDTO sheet = balanceSheetService.getBalanceSheet(userId);
			sheet = balanceSheetService.credit(new BigDecimal(1), new Date(), sheet);
			balanceSheetService.saveBalanceSheet(sheet);
			
			user.setFbLiked(true);
			userDao.save(user);
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "lotterySplitTransactionManager")
	public void payment(Payment payment, Long userId){

		paymentDao.save(payment);
		
		Balance creditBalance = balanceDao.findBalanceByUserId(userId);
		creditBalance.setLastModified(new Date());
		creditBalance.setAmount(creditBalance.getAmount().add(payment.getAmount()).round(MathContext.DECIMAL32));
		balanceDao.save(creditBalance);

		Transaction credit = new Transaction();
		credit.setType(Transaction.PAYMENT);
		credit.setUserId(userId);
		credit.setAmount(payment.getAmount());
		credit.setCreatedOn(payment.getCreatedOn());
		credit.setCurrentBalance(creditBalance.getAmount());
		transactionDao.save(credit);

		BalanceSheetDTO sheet = balanceSheetService.getBalanceSheet(userId);
		sheet = balanceSheetService.credit(payment.getAmount(), payment.getCreatedOn(), sheet);
		balanceSheetService.saveBalanceSheet(sheet);
	}	

	@Transactional(rollbackFor = Exception.class, value = "lotterySplitTransactionManager")
	public Long listLotteryTicket(Long userId, LotteryTicket lotteryTicket) throws ListException {
		Date today = new Date();
		
		User user = userDao.get(userId);

		Balance creditBalance = balanceDao.findBalanceByUserId(userId);

		// no duplicate lottery tickets
		/*
		if(imageService.existImage(lotteryTicket.getImageBytes()) == true){
			logger.warn("Lottery ticket image already exist.");
			return Constants.ListLottery.DUPLICATE;
		}
		*/

		BigDecimal virtualCost = new BigDecimal(0);

		if(userId.equals(1L) == false){
			virtualCost = (new BigDecimal(1.00)).divide(new BigDecimal(lotteryTicket.getSplitNumber()), 10, RoundingMode.HALF_UP).round(MathContext.DECIMAL32);
		}
		
		lotteryTicket.setCost(virtualCost.multiply(new BigDecimal(1.50)).round(MathContext.DECIMAL32));
        lotteryTicket.setUserId(userId);
        lotteryTicket.setAlreadyPurchased(true);
        lotteryTicket.setLastModified(new Date());
        lotteryTicket.setBuyerCount(0);
        lotteryTicket.setNumbers(StringUtils.trim(lotteryTicket.getNumbers()));
        lotteryTicket.setReferralCode(user.getReferralCode());
        lotteryTicket.setFacebookId(user.getFacebookId());
		
		if(lotteryTicket.getTicketDate().before(today)){
			return Constants.ListLottery.EXPIRED;
		}
		
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

        try{
        	lotteryTicket = lotteryTicketDao.save(lotteryTicket);
        	lotteryTicketDao.flush();
        }catch(Exception e){
        	throw new ListException("Unable to save listing");
        }

		if(lotteryTicket.getSplitNumber() > 0){
			BigDecimal rewardAmount = new BigDecimal(.10);
			try {
				Transaction reward = new Transaction();
				reward.setType(Transaction.REWARD);
				reward.setUserId(userId);
				reward.setLotteryTicketId(lotteryTicket.getId());
				reward.setAmount(rewardAmount);
				reward.setCreatedOn(today);
				reward.setCurrentBalance(creditBalance.getAmount().add(rewardAmount).round(MathContext.DECIMAL32));
				transactionDao.save(reward);
		    }catch(Exception e){
		    	logger.error("Reward Transaction " + e.getMessage());
		    	throw new ListException("Unable to save listing");
		    }
			
			try{
				creditBalance.setLastModified(today);
				creditBalance.setAmount(creditBalance.getAmount().add(rewardAmount).round(MathContext.DECIMAL32));
				balanceDao.save(creditBalance);	
			}catch(Exception e){
		    	logger.error("Debit Balance " + e.getMessage());
		    	throw new ListException("Unable to save listing");
			}
	
			try {
				BalanceSheetDTO sellerSheet = balanceSheetService.getBalanceSheet(userId);
				sellerSheet = balanceSheetService.credit(rewardAmount, today, sellerSheet);
				balanceSheetService.saveBalanceSheet(sellerSheet);
			}catch(Exception e){
		    	logger.error("Balance Sheet " + e.getMessage());
		    	throw new ListException("Unable to save listing");
			}
		}
		
		//imageService.saveImage(userId, lotteryTicket.getImageBytes());
		
		groupLotteryService.saveOrUpdateGroupTicket(lotteryTicket.getGroupName(), lotteryTicket.getId());
		
		return lotteryTicket.getId();
	}
	
	@Transactional(rollbackFor = Exception.class, value = "lotterySplitTransactionManager")
	public Integer freeplay(Long userId, Long buyerUserId, Long lotteryTicketId, Integer splitNumber, BigDecimal cost, String numbers, Date ticketDate) throws BuyException {    
    	Date today = new Date();

		User buyerUser = userDao.get(buyerUserId);
		User user = userDao.get(userId);
		
		LotteryTicket ticket = lotteryTicketDao.get(lotteryTicketId);
		
		if(ticket.getBuyerCount() == ticket.getSplitNumber()){
			return Constants.BuyLottery.OUT_OF_TICKETS;
		}
		
		ticket.setBuyerCount(ticket.getBuyerCount() + 1);
		lotteryTicketDao.save(ticket);
		
		LotteryBuyer buyer = new LotteryBuyer();
		try {
			buyer.setBuyerUserId(buyerUserId);
			buyer.setUserId(userId);
			buyer.setCost(new BigDecimal(0));	
			buyer.setCreatedOn(today);
			buyer.setLotteryTicketId(lotteryTicketId);
			buyer.setNumbers(numbers);
			buyer.setFacebookId(buyerUser.getFacebookId());
			buyer.setSellerFacebookId(user.getFacebookId());
			buyer.setPaid(true);
			buyer.setTicketDate(ticketDate);
			buyer.setReferralCode(buyerUser.getReferralCode());
			lotteryBuyerDao.save(buyer);
		}catch(Exception e){
			logger.error("LotteryBuyer: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		return Constants.BuyLottery.FREEBIE;
	}

	@Transactional(rollbackFor = Exception.class, value = "lotterySplitTransactionManager")
	public Integer buy(Long userId, Long buyerUserId, Long lotteryTicketId, Integer splitNumber, BigDecimal cost, String numbers, Date ticketDate) throws BuyException {
		LotteryTicket ticket = lotteryTicketDao.get(lotteryTicketId);
		
		LotteryBuyer alreadyBought = lotteryBuyerDao.findAlreadyBought(buyerUserId, lotteryTicketId);
		if(alreadyBought != null){
			return Constants.BuyLottery.DUPLICATE_PURCHASE;
		}
		
		if(ticket.getUserId().equals(1L)){
			Integer resultOfFreebie = freeplay(userId, buyerUserId, lotteryTicketId,splitNumber,cost,numbers,ticketDate);
	
			if(Constants.BuyLottery.FREEBIE == resultOfFreebie){
				return Constants.BuyLottery.FREEBIE;
			}else if(Constants.BuyLottery.FREEBIE_ERROR == resultOfFreebie ){
				return Constants.BuyLottery.FREEBIE_ERROR;
			}
		}

		Balance debitBalance = balanceDao.findBalanceByUserId(buyerUserId);

		BigDecimal virtualCost = (new BigDecimal(1.00)).divide(new BigDecimal(splitNumber), 10, RoundingMode.HALF_UP).round(MathContext.DECIMAL32);
		
		if(debitBalance.getAmount().compareTo(new BigDecimal(0)) <= 0){
			return Constants.BuyLottery.OUT_OF_CASH;
		}		
		
		Date today = new Date();
		
		User buyerUser = userDao.get(buyerUserId);
		User user = userDao.get(userId);

		if(ticket.getBuyerCount() == ticket.getSplitNumber()){
			return Constants.BuyLottery.OUT_OF_TICKETS;
		}
		
		ticket.setBuyerCount(ticket.getBuyerCount() + 1);
		lotteryTicketDao.save(ticket);
		
		LotteryBuyer buyer = new LotteryBuyer();
		try {
			buyer.setBuyerUserId(buyerUserId);
			buyer.setUserId(userId);
			buyer.setCost(virtualCost.multiply(new BigDecimal(1.50)).round(MathContext.DECIMAL32));	
			buyer.setCreatedOn(today);
			buyer.setLotteryTicketId(lotteryTicketId);
			buyer.setNumbers(numbers);
			buyer.setFacebookId(buyerUser.getFacebookId());
			buyer.setSellerFacebookId(user.getFacebookId());
			buyer.setPaid(true);
			buyer.setTicketDate(ticketDate);
			buyer.setReferralCode(buyerUser.getReferralCode());
			lotteryBuyerDao.save(buyer);
		}catch(Exception e){
			logger.error("LotteryBuyer: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		Balance creditBalance = balanceDao.findBalanceByUserId(userId);
	
		Transaction debit = new Transaction();
		try {
			debit.setType(Transaction.DEBIT);
			debit.setLotteryTicketId(ticket.getId());
			debit.setUserId(buyerUserId);
			debit.setAmount(virtualCost.multiply(new BigDecimal(1.50)).round(MathContext.DECIMAL32));
			debit.setCreatedOn(today);
			debit.setCurrentBalance(debitBalance.getAmount().subtract(virtualCost.multiply(new BigDecimal(1.50)).round(MathContext.DECIMAL32)).round(MathContext.DECIMAL32));
			transactionDao.save(debit);
		}catch(Exception e){
			logger.error("DEBIT Transaction: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		try {
			Transaction credit = new Transaction();
			credit.setType(Transaction.CREDIT);
			credit.setLotteryTicketId(ticket.getId());
			credit.setUserId(userId);
			credit.setAmount(virtualCost);
			credit.setCreatedOn(today);
			credit.setCurrentBalance(creditBalance.getAmount().add(virtualCost).round(MathContext.DECIMAL32));
			transactionDao.save(credit);
		}catch(Exception e){
			logger.error("Credit Transaction: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		try{
			Transaction fee = new Transaction();
			fee.setType(Transaction.FEE);
			fee.setUserId(userId);
			fee.setLotteryTicketId(ticket.getId());
			fee.setAmount(virtualCost.multiply(new BigDecimal(0.50)).round(MathContext.DECIMAL32));
			fee.setCreatedOn(today);
			fee.setCurrentBalance(creditBalance.getAmount().subtract(virtualCost.multiply(new BigDecimal(0.50)).round(MathContext.DECIMAL32)));
			transactionDao.save(fee);
		}catch(Exception e){
			logger.error("Fee Transaction: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		try {
			creditBalance.setLastModified(today);
			creditBalance.setAmount(creditBalance.getAmount().add((virtualCost.subtract(virtualCost.multiply(new BigDecimal(0.50)).round(MathContext.DECIMAL32)))).round(MathContext.DECIMAL32));
			creditBalance.setTransactionCount(creditBalance.getTransactionCount() + 1);
			creditBalance.setEarned(creditBalance.getEarned().add(virtualCost).round(MathContext.DECIMAL32));
			creditBalance.setBillable(creditBalance.getBillable().add(virtualCost.multiply(new BigDecimal(0.50)).round(MathContext.DECIMAL32)));
			creditBalance.setEarnedCount(creditBalance.getEarnedCount() + 1);
			balanceDao.save(creditBalance);	
		}catch(Exception e){
			logger.error("Credit Balance: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		try {
			debitBalance.setLastModified(today);
			debitBalance.setAmount(debitBalance.getAmount().subtract(virtualCost).round(MathContext.DECIMAL32));
			debitBalance.setSpent(debitBalance.getSpent().add(virtualCost).round(MathContext.DECIMAL32));
			debitBalance.setSpentCount(debitBalance.getSpentCount() + 1);
			balanceDao.save(debitBalance);	
		}catch(Exception e){
			logger.error("Debit Balance: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}

		try {
			BalanceSheetDTO sellerSheet = balanceSheetService.getBalanceSheet(userId);
			sellerSheet = balanceSheetService.credit(virtualCost, today, sellerSheet);
			sellerSheet = balanceSheetService.fee(virtualCost.multiply(new BigDecimal(0.50)).round(MathContext.DECIMAL32), today, sellerSheet);
			balanceSheetService.saveBalanceSheet(sellerSheet);
	
			BalanceSheetDTO buyerSheet = balanceSheetService.getBalanceSheet(buyerUserId);
			buyerSheet = balanceSheetService.debit(virtualCost, today, buyerSheet);
			balanceSheetService.saveBalanceSheet(buyerSheet);
		}catch(Exception e){
			logger.error("Balance Sheet: " + e.getMessage());
			throw new BuyException("Unable to buy ticket");
		}
		
		if(StringUtils.isNotEmpty(buyerUser.getReferredBy()) && buyerUser.getAwarded() == false) {
			userDao.updateReferralCount(buyerUser.getReferredBy());
			buyerUser.setAwarded(true);
			userDao.save(buyerUser);
			
			User refererUser = userDao.findByReferralCode(buyerUser.getReferredBy());
	        
	        if((refererUser.getReferralCount() + 1) % 1000 == 0){
		        // Send an account information e-mail
		        message.setSubject("New 1000 Referrals");
		        try {
		        	EMailUtil.sendMessage(templateName, message, mailEngine, refererUser, "");
		        } catch (final MailException me) {
		        	return Constants.BuyLottery.REFERER_MAIL;
		        }       
	        }
		}	

		return 1;
	}

	public List<Transaction> findTransactionsByUserId(Long userId, int start) {
		return this.transactionDao.findTransactionsByUserId(userId, start);
	}

	@Override
	public Integer getAllMyTransactionsCount(Long userId) {
		return this.transactionDao.getAllMyTransactionsCount(userId);
	}

	@Override
	public Long orderLotteryTicket(Long userId, LotteryTicket lotteryTicket)
			throws ListException {
		Date today = new Date();
		
		User user = userDao.get(userId);

		Balance debitBalance = balanceDao.findBalanceByUserId(userId);
		
		BigDecimal charge = (new BigDecimal(1.00)).multiply(new BigDecimal(1.50).round(MathContext.DECIMAL32)).round(MathContext.DECIMAL32);
		
		BigDecimal virtualCost = (new BigDecimal(1.00)).divide(new BigDecimal(lotteryTicket.getSplitNumber()), 10, RoundingMode.HALF_UP).round(MathContext.DECIMAL32);

		if(userId.equals(1L) == true){
			virtualCost = new BigDecimal(0);
			charge = new BigDecimal(0);
		}

		lotteryTicket.setCost(virtualCost.multiply(new BigDecimal(1.50)).round(MathContext.DECIMAL32));
        lotteryTicket.setUserId(userId);
        lotteryTicket.setAlreadyPurchased(false);
        lotteryTicket.setLastModified(new Date());
        lotteryTicket.setBuyerCount(0);
        lotteryTicket.setNumbers(StringUtils.trim(lotteryTicket.getNumbers()));
        lotteryTicket.setReferralCode(user.getReferralCode());
		lotteryTicket.setFacebookId(user.getFacebookId());
		if(lotteryTicket.getTicketDate().before(today)){
			return Constants.ListLottery.EXPIRED;
		}
		
        try{
        	lotteryTicket = lotteryTicketDao.save(lotteryTicket);
        	lotteryTicketDao.flush();
        }catch(Exception e){
        	throw new ListException("Unable to order listing");
        }
        
        if(charge.equals(new BigDecimal(0))){
			try{
				Transaction fee = new Transaction();
				fee.setType(Transaction.FEE);
				fee.setUserId(userId);
				fee.setLotteryTicketId(lotteryTicket.getId());
				fee.setAmount(charge);
				fee.setCreatedOn(today);
				fee.setCurrentBalance(debitBalance.getAmount().add(charge).round(MathContext.DECIMAL32));
				transactionDao.save(fee);
			}catch(Exception e){
				logger.error("Fee Transaction: " + e.getMessage());
				throw new ListException("Unable to order ticket");
			}        
	
			try{
				debitBalance.setLastModified(today);
				debitBalance.setAmount(debitBalance.getAmount().subtract(charge).round(MathContext.DECIMAL32));
				balanceDao.save(debitBalance);	
			}catch(Exception e){
		    	logger.error("Debit Balance " + e.getMessage());
		    	throw new ListException("Unable to order listing");
			}
	
			try {
				BalanceSheetDTO sellerSheet = balanceSheetService.getBalanceSheet(userId);
				sellerSheet = balanceSheetService.debit(charge, today, sellerSheet);
				balanceSheetService.saveBalanceSheet(sellerSheet);
			}catch(Exception e){
		    	logger.error("Balance Sheet " + e.getMessage());
		    	throw new ListException("Unable to order listing");
			}
        }
		
		groupLotteryService.saveOrUpdateGroupTicket(lotteryTicket.getGroupName(), lotteryTicket.getId());
		
		return lotteryTicket.getId();
	}
}
