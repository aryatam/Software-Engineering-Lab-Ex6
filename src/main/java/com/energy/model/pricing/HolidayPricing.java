package com.energy.model.pricing;

import com.energy.model.EnergyConstants;

public class HolidayPricing implements EnergyPricingStrategy {
    @Override public String name() { return "Holiday"; }
    @Override public double costPerUnit() { return EnergyConstants.COST_HOLIDAY; }
}
