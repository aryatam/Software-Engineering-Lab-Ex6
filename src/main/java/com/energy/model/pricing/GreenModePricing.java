package com.energy.model.pricing;


import com.energy.model.EnergyConstants;

public class GreenModePricing implements EnergyPricingStrategy {
    @Override public String name() { return "Green Mode"; }
    @Override public double costPerUnit() { return EnergyConstants.COST_GREEN; }
}