package com.simulator.model;

public class ApiResponse {
    private String vehicleType;
    private String vehicleCondition;
    private int vehicleYear;
    private long totalLoanAmount;
    private int loanTenure;
    private long downPayment;

    public String getVehicleType() { return vehicleType; }
    public String getVehicleCondition() { return vehicleCondition; }
    public int getVehicleYear() { return vehicleYear; }
    public long getTotalLoanAmount() { return totalLoanAmount; }
    public int getLoanTenure() { return loanTenure; }
    public long getDownPayment() { return downPayment; }
}