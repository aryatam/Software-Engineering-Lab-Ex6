package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;
import com.energy.model.EnergyConstants;


public class ShutdownState implements SystemState {
    @Override public String name() { return "Shutdown"; }
    @Override public double consumptionMultiplier() { return EnergyConstants.MUL_SHUTDOWN; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("خاموشی کامل (Shutdown): همه سیستم‌ها خاموش هستند.");
    }
}