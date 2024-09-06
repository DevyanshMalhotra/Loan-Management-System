package com.fi.loanapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fi.loanapp.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, String> {

    List<Loan> findAllByFkCustomerId(String fkCustomerId);

    List<Loan> findByLoanId(String loanId);

    List<Loan> findByLoanStatus(String loanStatus);
}
