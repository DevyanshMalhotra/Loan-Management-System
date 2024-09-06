package com.fi.loanapp.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.format.annotation.DateTimeFormat;

import com.fi.loanapp.entity.Loan;
import com.fi.loanapp.service.LoanService;

@Controller
@SessionAttributes({ "customer_id", "loan_id" })
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/saveLoan")
    public String saveLoan(Model model, @ModelAttribute("objLoan") Loan loan,
            @RequestParam("suretyAadhaarCard") String suretyAadhaarCard,
            @RequestParam("suretyPanCard") String suretyPanCard,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("maturityDate") java.util.Date maturityDate) {
        String customerId = (String) model.getAttribute("customer_id");
        loan.setFkCustomerId(customerId);

        // Set surety information
        loan.setSuretyAadhaarCard(suretyAadhaarCard);
        loan.setSuretyPanCard(suretyPanCard);

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlMaturityDate = new java.sql.Date(maturityDate.getTime());
        // Set the converted date to your loan object
        loan.setMaturityDate(sqlMaturityDate);

        String loanId = loanService.saveLoan(loan, customerId, suretyAadhaarCard, suretyPanCard);
        if (loanId != null) {
            model.addAttribute("loan_id", loanId);
            return "redirect:/savedLoan";
        } else {
            // Handle error (e.g., display error message to user)
            return "errorPage";
        }
    }

    @GetMapping("/savedLoan")
    public String getSavedLoan() {
        return "savedLoan";
    }

    @GetMapping("/applyLoan")
    public String getApplyLoan(Model model) {
        Loan loan = new Loan();
        model.addAttribute("objLoan", loan);
        return "applyLoan";
    }

    @GetMapping("/EmiCalculator")
    public String getEmiCalculator() {
        return "emiCalculator";
    }

    @PostMapping("/approve")
    public String approveLoan(@RequestParam("loanId") String loanId) {
        String loan = loanService.updateLoanStatus(loanId, "approved");
        if (loan == null) {
            return "error bhetla";
        }
        return "redirect:/AdminDashboard";
    }

    @PostMapping("/reject")
    public String rejectLoan(@RequestParam("loanId") String loanId) {
        String loan = loanService.updateLoanStatus(loanId, "rejected");
        if (loan == null) {
            return "error bhetla";
        }
        return "redirect:/AdminDashboard";
    }
}
