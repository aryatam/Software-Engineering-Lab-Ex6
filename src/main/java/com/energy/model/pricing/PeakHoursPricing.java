package com.energy.model.pricing;


public class PeakHoursPricing implements EnergyPricingStrategy {
    @Override public String name() { return "Peak Hours"; }
    @Override public double costPerUnit() { return 1000; }
}