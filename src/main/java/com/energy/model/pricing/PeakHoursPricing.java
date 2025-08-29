package com.energy.model.pricing;


import com.energy.model.EnergyConstants;

public class PeakHoursPricing implements EnergyPricingStrategy {
    @Override public String name() { return "Peak Hours"; }
    @Override public double costPerUnit() { return EnergyConstants.COST_PEAK; }
}