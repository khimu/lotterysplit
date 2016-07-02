package com.share.lottery.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.lottery.dao.IPaymentReportDao;
import com.share.lottery.model.PaymentReport;
import com.share.lottery.model.PaymentReportDTO;
import com.share.lottery.service.IPaymentReportManager;

@Transactional("lotterySplitTransactionManager")
@Service("paymentReportService")
public class PaymentReportManager extends GenericManagerImpl<PaymentReport, Long> implements IPaymentReportManager {

	public PaymentReportManager(){}

	@Autowired
    public PaymentReportManager(@Qualifier("paymentReportDao") IPaymentReportDao paymentReportDao) {
        this.dao = paymentReportDao;
    }
	
	public List<PaymentReport> findByCustomerId(Integer customerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);
		
		return dao.findByNamedQuery("findByCustomerId", map);
	}
	
	public Map<Integer, PaymentReportDTO> getMonthlyReport(Integer customerId, Date startDate, Date endDate){
		Map<Integer, PaymentReportDTO> monthlyData = new TreeMap<Integer, PaymentReportDTO>(new PaymentReportComparator());
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("customerId", customerId);
		map.put("startDate", format.format(startDate));
		map.put("endDate", format.format(endDate));

		List<PaymentReport> records = dao.findByNamedQuery("findByCustomerIdForDates", map);
		
		for(PaymentReport report : records){
			if(report.getSuccess() != 1){
				continue;
			}
			PaymentReportDTO dto = monthlyData.get(report.getMonth());
			if(dto == null){
				dto = new PaymentReportDTO(0.00, report.getAmount(), report.getAmount());
			}else{
				dto.setEndingBalance(dto.getEndingBalance() + report.getAmount());
				dto.setPaymentReceived(dto.getPaymentReceived() + report.getAmount());
			}
			
			int nextMonth = report.getMonth() + 1;
			PaymentReportDTO nextDto = monthlyData.get(nextMonth);
			if(nextDto != null){
				nextDto.setBeginningBalance(dto.getEndingBalance());
				nextDto.setEndingBalance(dto.getEndingBalance() + nextDto.getPaymentReceived());
			}else{
				nextDto = new PaymentReportDTO(dto.getEndingBalance(), dto.getEndingBalance(), 0.00);
			}
			
			monthlyData.put(nextMonth, nextDto);
			monthlyData.put(report.getMonth(), dto);
		}
		
		return monthlyData;
	}
	
	class PaymentReportComparator implements Comparator<Integer>{
		 
	    @Override
	    public int compare(Integer key1, Integer key2) {
	        return key2.compareTo(key1);
	    }
	     
	}
}
