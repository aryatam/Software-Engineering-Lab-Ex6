package com.energy.model;

import com.energy.model.pricing.*;
import com.energy.model.state.*;

public class BuildingEnergySystem {

    private EnergyPricingStrategy pricing;
    private SystemState state;

    // Extract Class: به‌جای double totalUnits
    private final ConsumptionTracker tracker = new ConsumptionTracker();

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

    // نمایش وضعیت + مصرف تجمعی از Tracker
    public String status() {
        return String.format(
                "وضعیت: %s | تعرفه: %s (%,.0f تومان/واحد) | مصرف تجمعی: %,.1f واحد",
                getState().name(), getPricing().name(), getPricing().costPerUnit(), getTotalUnits()
        );
    }

    // Query: فقط محاسبه هزینه (بدون تغییر وضعیت/مصرف)
    public double calculateCost(double units) {
        if (units < 0) throw new IllegalArgumentException("units cannot be negative");
        double effectiveUnits = units * getState().consumptionMultiplier();
        return getPricing().calculate(effectiveUnits);
    }

    // Modifier: تفویض به Tracker
    public void addConsumption(double units) {
        tracker.add(units);
    }

    // Query: تفویض به Tracker
    public double getTotalUnits() {
        return tracker.total();
    }

    public void log(String msg) { System.out.println(msg); }
}
