package com.energy.model;


import com.energy.model.pricing.*;
import com.energy.model.state.*;


public class BuildingEnergySystem {
    private EnergyPricingStrategy pricing;
    private SystemState state;


    public BuildingEnergySystem() {
        this.pricing = new StandardPricing();
        this.state = new ActiveState();
        this.state.onEnter(this);
    }


    public void setPricing(EnergyPricingStrategy pricing) {
        if (pricing == null) throw new IllegalArgumentException("pricing null");
        this.pricing = pricing;
    }


    public void setState(SystemState newState) {
        if (newState == null) throw new IllegalArgumentException("state null");
        if (this.state != null) this.state.onExit(this);
        this.state = newState;
        this.state.onEnter(this);
    }


    public String status() {
        return String.format("وضعیت: %s | تعرفه: %s (%,.0f تومان/واحد)",
                state.name(), pricing.name(), pricing.costPerUnit());
    }


    public double calculateCost(double units) {
        if (units < 0) throw new IllegalArgumentException("units cannot be negative");
        double effectiveUnits = units * state.consumptionMultiplier();
        return pricing.calculate(effectiveUnits);
    }


    // Logger ساده برای UI
    public void log(String msg) { System.out.println(msg); }


    // Getters برای UI/تست
    public EnergyPricingStrategy getPricing() { return pricing; }
    public SystemState getState() { return state; }
}