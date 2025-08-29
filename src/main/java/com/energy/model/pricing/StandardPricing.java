package com.energy.model.pricing;


public class StandardPricing implements EnergyPricingStrategy {
    @Override public String name() { return "Standard"; }
    @Override public double costPerUnit() { return 500; }
}