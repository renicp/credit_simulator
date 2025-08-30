package com.simulator.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.simulator.model.Loan;
import com.simulator.service.LoanCalculatorService;

public class LoanCalculator implements LoanCalculatorService {
    public List<String> calculateInstallments(Loan loan) {
        List<String> results = new ArrayList<>();

        if (loan.getTenor() <= 0) {
            throw new IllegalArgumentException("Tenor harus > 0");
        }

        BigDecimal monthsTotal = BigDecimal.valueOf(12L * loan.getTenor());
        BigDecimal principal = BigDecimal.valueOf(loan.getTotalLoan())
                .subtract(BigDecimal.valueOf(loan.getDownPayment()));


        BigDecimal rate = BigDecimal.valueOf(loan.getVehicle().getBaseInterest())
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal outstanding = principal.multiply(rate).add(principal);

        for (int b = 0; b < loan.getTenor(); b++) {
            BigDecimal denom = monthsTotal.subtract(BigDecimal.valueOf(12L * b));
            BigDecimal monthly = outstanding.divide(denom, 6, RoundingMode.HALF_UP);
            BigDecimal annualTotal = monthly.multiply(BigDecimal.valueOf(12));
            double displayRatePercent = rate.multiply(BigDecimal.valueOf(100)).doubleValue();
            BigDecimal monthlyFormatted = monthly.setScale(3, RoundingMode.HALF_UP);
            int yearNumber = b + 1;
            String line = String.format("Tahun %d : Rp.%s Rate : %s",
                    yearNumber,
                    monthlyFormatted.toPlainString(),
                    removeTrailingZeros(displayRatePercent));
            results.add(line);
            if (yearNumber % 2 == 0) {
                rate = rate.add(new BigDecimal("0.005"));
            } else {
                rate = rate.add(new BigDecimal("0.001"));
            }
            outstanding = outstanding.subtract(annualTotal);
            outstanding = outstanding.multiply(rate).add(outstanding);
        }

        return results;
    }

    private String removeTrailingZeros(double d) {
        BigDecimal bd = BigDecimal.valueOf(d).stripTrailingZeros();
        return bd.toPlainString();
    }
}