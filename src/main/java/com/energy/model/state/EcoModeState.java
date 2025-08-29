package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;


public class EcoModeState implements SystemState {
    @Override public String name() { return "Eco Mode"; }
    @Override public double consumptionMultiplier() { return 0.4; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("حالت اقتصادی (Eco): تنها سیستم‌های حیاتی فعال هستند.");
    }
}