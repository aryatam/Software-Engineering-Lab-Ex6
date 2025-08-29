package com.energy.model.pricing;


public interface EnergyPricingStrategy {
    String name();
    double costPerUnit();
    default double calculate(double units) {
        if (units < 0) throw new IllegalArgumentException("units cannot be negative");
        return units * costPerUnit();
    }
}