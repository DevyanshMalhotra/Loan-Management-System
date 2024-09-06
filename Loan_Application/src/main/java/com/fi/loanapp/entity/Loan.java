package com.fi.loanapp.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "loans")
public class Loan {

	@Id
	@Column(name = "loan_id", nullable = false)
	private String loanId;

	@Column(name = "loan_type")
	private String loanType;

	@Column(name = "loan_reason")
	private String loanReason;

	@Column(name = "loan_amount", nullable = false)
	private long loanAmount;

	@Column(name = "interest_rate")
	private int interestRate = 13;

	@Column(name = "trade_date")
	private Date tradeDate;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "loan_tenure")
	private int loanTenure;

	@Column(name = "loan_terms")
	private int loanTerms;

	@Column(name = "payment_cycles")
	private int paymentCycles;

	@Column(name = "maturity_date")
	private Date maturityDate;

	@Column(name = "emi")
	private long emi;

	@Column(name = "total_amount")
	private long totalAmount;

	@Column(name = "paid_amount")
	private long paidAmount = 0;

	@Column(name = "loan_status")
	private String loanStatus = "pending";

	@NotBlank(message = "Aadhaar card number is required")
	@Column(name = "aadhaar_card")
	private String aadhaarCard;

	@NotBlank(message = "PAN card number is required")
	@Column(name = "pan_card")
	private String panCard;

	@Column(name = "employment_id")
	private String employmentId;

	@Column(name = "occupation")
	private String occupation;

	@NotNull(message = "Monthly income is required")
	@Positive(message = "Monthly income must be positive")
	@Column(name = "monthly_income")
	private long monthlyIncome;

	@Column(name = "cibil_score")
	private int cibilScore;

	@Column(name = "surety_aadhaar_card")
	private String suretyAadhaarCard;

	@Column(name = "surety_pan_card")
	private String suretyPanCard;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_loan_id", referencedColumnName = "loan_id")
	private List<Payment> payment;

	@Column(name = "fk_customer_id", nullable = false)
	private String fkCustomerId;

	@ManyToOne
	@JoinColumn(name = "fk_customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
	private Customer customer;

	public Loan(String loanId, long loanAmount, String loanType, String loanReason, int loanTenure, String loanStatus,
			Customer customer) {
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.loanType = loanType;
		this.loanReason = loanReason;
		this.loanTenure = loanTenure;
		this.loanStatus = loanStatus;
		this.customer = customer;
	}

	// Constructor including surety information
	public Loan(String loanId, long loanAmount, String loanType, String loanReason, int loanTenure, String loanStatus,
			String suretyAadhaarCard, String suretyPanCard, Customer customer) {
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.loanType = loanType;
		this.loanReason = loanReason;
		this.loanTenure = loanTenure;
		this.loanStatus = loanStatus;
		this.suretyAadhaarCard = suretyAadhaarCard;
		this.suretyPanCard = suretyPanCard;
		this.customer = customer;
	}
}
