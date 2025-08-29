package com.energy.model.pricing;


public class GreenModePricing implements EnergyPricingStrategy {
    @Override public String name() { return "Green Mode"; }
    @Override public double costPerUnit() { return 300; }
}