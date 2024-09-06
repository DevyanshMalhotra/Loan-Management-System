package com.fi.loanapp.service;

import java.util.List;
import com.fi.loanapp.entity.Loan;

public interface LoanService {

    /**
     * Saves a new loan record in the database.
     * 
     * @param loan              The loan object to be saved.
     * @param customerId        The ID of the customer associated with the loan.
     * @param suretyAadhaarCard The Aadhaar card number of the surety.
     * @param suretyPanCard     The PAN card number of the surety.
     * @return The ID of the saved loan if successful, null otherwise.
     */
    public String saveLoan(Loan loan, String customerId, String suretyAadhaarCard, String suretyPanCard);

    public List<Loan> getByFkCustomerId(String fkCustomerId);

    public List<Loan> getPendingLoan();

    public Loan getLoan(String loanId);

    /**
     * Updates the status of a loan in the database.
     * 
     * @param loanId     The ID of the loan to update.
     * @param loanStatus The new status of the loan.
     * @return A string indicating the result of the update operation (e.g., "success" or an error message).
     */
    public String updateLoanStatus(String loanId, String loanStatus);

}
