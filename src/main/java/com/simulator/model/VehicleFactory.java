package com.simulator.model;

public class VehicleFactory {
    public static Vehicle create(String type, String condition) {
        type = type.toLowerCase();
        condition = condition.toLowerCase();

        if (type.equals("mobil")) {
            return new Car(condition);
        } else if (type.equals("motor")) {
            return new Motorcycle(condition);
        } else {
            throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}