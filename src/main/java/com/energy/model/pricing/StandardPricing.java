package com.energy.model.pricing;


import com.energy.model.EnergyConstants;

public class StandardPricing implements EnergyPricingStrategy {
    @Override public String name() { return "Standard"; }
    @Override public double costPerUnit() { return EnergyConstants.COST_STANDARD; }
}