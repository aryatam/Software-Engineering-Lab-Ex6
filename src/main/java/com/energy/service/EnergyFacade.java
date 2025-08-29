package com.energy.service;

import com.energy.model.BuildingEnergySystem;
import com.energy.model.pricing.PricingOption;
import com.energy.model.state.StateOption;

public class EnergyFacade {
    private final BuildingEnergySystem system;

    public EnergyFacade(BuildingEnergySystem system) {
        this.system = system;
    }

    public void selectPricing(PricingOption option) {
        system.setPricing(option.strategy());
    }

    public void selectState(StateOption option) {
        system.setState(option.state());
    }

    public double previewCost(double units) {
        return system.calculateCost(units);
    }

    public void recordConsumption(double units) {
        system.addConsumption(units);
    }

    public String status() {
        return system.status();
    }
}
