package com.energy.model;

public class ConsumptionTracker {
    private double totalUnits = 0.0;

    public void add(double units) {
        if (units < 0) throw new IllegalArgumentException("units cannot be negative");
        totalUnits += units;
    }

    public double total() {
        return totalUnits;
    }

    public void reset() {
        totalUnits = 0.0;
    }
}
