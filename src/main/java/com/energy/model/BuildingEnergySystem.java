package com.energy.model;

import com.energy.model.pricing.*;
import com.energy.model.state.*;

public class BuildingEnergySystem {
    private EnergyPricingStrategy pricing;
    private SystemState state;

    public BuildingEnergySystem() {
        setPricing(new StandardPricing());
        setState(new ActiveState());
        getState().onEnter(this);
    }

    public EnergyPricingStrategy getPricing() { return pricing; }
    public void setPricing(EnergyPricingStrategy p) {
        if (p == null) throw new IllegalArgumentException("pricing null");
        this.pricing = p;
    }

    public SystemState getState() { return state; }
    public void setState(SystemState s) {
        if (s == null) throw new IllegalArgumentException("state null");
        if (this.state != null) this.state.onExit(this);
        this.state = s;
        this.state.onEnter(this);
    }

    public String status() {
        return String.format("وضعیت: %s | تعرفه: %s (%,.0f تومان/واحد)",
                getState().name(), getPricing().name(), getPricing().costPerUnit());
    }

    public double calculateCost(double units) {
        if (units < 0) throw new IllegalArgumentException("units cannot be negative");
        double effectiveUnits = units * getState().consumptionMultiplier();
        return getPricing().calculate(effectiveUnits);
    }

    public void log(String msg) { System.out.println(msg); }
}
