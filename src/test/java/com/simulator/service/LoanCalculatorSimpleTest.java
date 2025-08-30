package com.simulator.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.simulator.model.Car;
import com.simulator.model.Loan;
import com.simulator.model.Motorcycle;
import com.simulator.service.impl.LoanCalculator;

class LoanCalculatorSimpleTest {

    @Test
    void testCalculateInstallments() {
        LoanCalculatorService calculator = new LoanCalculator();
        Car car = new Car("baru");
        Loan loan = new Loan(car, 2024, 100000000L, 2, 35000000L);
        
        List<String> results = calculator.calculateInstallments(loan);
        
        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.get(0).contains("Tahun 1"));
        assertTrue(results.get(1).contains("Tahun 2"));
    }

    @Test
    void testCalculateInstallmentsMotorcycle() {
        LoanCalculatorService calculator = new LoanCalculator();
        Motorcycle motorcycle = new Motorcycle("bekas");
        Loan loan = new Loan(motorcycle, 2023, 50000000L, 3, 15000000L);
        
        List<String> results = calculator.calculateInstallments(loan);
        
        assertNotNull(results);
        assertEquals(3, results.size());
        assertTrue(results.get(0).contains("Tahun 1"));
        assertTrue(results.get(1).contains("Tahun 2"));
        assertTrue(results.get(2).contains("Tahun 3"));
    }

    @Test
    void testCalculateInstallmentsWithZeroTenor() {
        LoanCalculatorService calculator = new LoanCalculator();
        Car car = new Car("baru");
        Loan invalidLoan = new Loan(car, 2024, 100000000L, 0, 35000000L);
        
        assertThrows(IllegalArgumentException.class, () -> 
            calculator.calculateInstallments(invalidLoan));
    }
}