package com.fi.loanapp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fi.loanapp.entity.Customer;
import com.fi.loanapp.entity.Loan;
import com.fi.loanapp.repository.CustomerRepository;
import com.fi.loanapp.repository.LoanRepository;
import com.fi.loanapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setCustomerId(generateKey("CID-"));
        String encryptedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return customerRepository.save(customer);
    }

    private String generateKey(String prefix) {
        String key = UUID.randomUUID().toString().split("-")[0];
        return prefix + key;
    }

    @Override
    public String getCustomerDetails(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return null; // Customer not found
        }

        // Use BCryptPasswordEncoder to verify the password
        if (passwordEncoder.matches(password, customer.getPassword())) {
            return customer.getCustomerId(); // Passwords match, return customer ID
        } else {
            return null; // Passwords don't match
        }
    }

    @Override
    public List<Customer> getCustomerProfile(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer getCustomer(Loan loan) {
        List<Loan> getLoans = loanRepository.findByLoanId(loan.getLoanId());
        for (Loan loans : getLoans) {
            System.out.println("^^^^" + loan.getFkCustomerId());
            if (loans.getLoanId().equals(loan.getLoanId())) {
                System.out.println("****" + loan.getFkCustomerId());
                List<Customer> customers = customerRepository.findByCustomerId(loan.getFkCustomerId());
                for (Customer customer : customers) {
                    System.out.println("!!!!!!" + customer.getAccno());
                    return customer;
                }

            }
        }
        return null;
    }
}
