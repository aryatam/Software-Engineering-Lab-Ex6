package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;
import com.energy.model.EnergyConstants;


public class EcoModeState implements SystemState {
    @Override public String name() { return "Eco Mode"; }
    @Override public double consumptionMultiplier() { return EnergyConstants.MUL_ECO; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("حالت اقتصادی (Eco): تنها سیستم‌های حیاتی فعال هستند.");
    }
}