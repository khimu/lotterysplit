package com.share.lottery.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.share.lottery.model.PaymentReport;
import com.share.lottery.model.PaymentReportDTO;

public interface IPaymentReportManager extends GenericManager<PaymentReport, Long> {

	public List<PaymentReport> findByCustomerId(Integer customerId);
	
	public Map<Integer, PaymentReportDTO> getMonthlyReport(Integer customerId, Date startDate, Date endDate);

}
