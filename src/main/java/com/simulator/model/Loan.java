package com.simulator.model;

public class Loan {
    private Vehicle vehicle;
    private int year;
    private long totalLoan;
    private int tenor;
    private long downPayment;

    public Loan(Vehicle vehicle, int year, long totalLoan, int tenor, long downPayment) {
        this.vehicle = vehicle;
        this.year = year;
        this.totalLoan = totalLoan;
        this.tenor = tenor;
        this.downPayment = downPayment;
    }

    public Vehicle getVehicle() { return vehicle; }
    public int getYear() { return year; }
    public long getTotalLoan() { return totalLoan; }
    public int getTenor() { return tenor; }
    public long getDownPayment() { return downPayment; }
}