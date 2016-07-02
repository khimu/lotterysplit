package com.share.lottery.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NamedNativeQueries({
	@NamedNativeQuery(name = "findByCustomerId", query = "select * from payment_report s where s.customer_id = :customerId", resultClass = PaymentReport.class),
	@NamedNativeQuery(name = "findByCustomerIdForDates", query = "select * from payment_report s where s.customer_id = :customerId and s.created_on_date between :startDate and :endDate order by s.month desc", resultClass = PaymentReport.class)
})
@Entity
@Table(name = "payment_report")
@Cache (usage=CacheConcurrencyStrategy.READ_ONLY) 
public class PaymentReport implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer version;
	private String transactionId;
	private Double amount;
	private String transactionMessage;
	private String createdOn;
	private String email;
	private String companyName;
	private Integer month;
	private Integer success;
	private String invoiceNumber;

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	@Id
	@Column(name="transaction_id", unique = true)
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name="amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name="transaction_message")
	public String getTransactionMessage() {
		return transactionMessage;
	}

	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}

	@Column(name="created_on_date")
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name="month")
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Column(name="invoice_number")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("transactionId", this.transactionId)
				.append("amount", this.amount)
				.append("transactionMessage", this.transactionMessage)
				.append("companyName", this.companyName)
				.append("month", this.month)
				.append("email", this.email)
				.append("success", this.success)
				.append("createdOn", this.createdOn);

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof PaymentReport)) {
			return false;
		}
		if (o.getClass() != getClass()) {
			return false;
		}
		if (this == o) {
			return true;
		}

		final PaymentReport user = (PaymentReport) o;

		return new EqualsBuilder().append(transactionId, user.getTransactionId()).isEquals();
	}

	@Override
	public int hashCode() {
		return (transactionId != null ? transactionId.hashCode() : 0);
	}

}
