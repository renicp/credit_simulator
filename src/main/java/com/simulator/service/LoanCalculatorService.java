package com.simulator.service;

import com.simulator.model.Loan;
import java.util.List;

public interface LoanCalculatorService {
    List<String> calculateInstallments(Loan loan);
}