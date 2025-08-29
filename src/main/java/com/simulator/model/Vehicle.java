package com.simulator.model;

public abstract class Vehicle {
    protected String type;
    protected String condition;
    protected double baseInterest;

    public Vehicle(String type, String condition, double baseInterest) {
        this.type = type;
        this.condition = condition;
        this.baseInterest = baseInterest;
    }

    public String getType() { return type; }
    public String getCondition() { return condition; }
    public double getBaseInterest() { return baseInterest; }
}