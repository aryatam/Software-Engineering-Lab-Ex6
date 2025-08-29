package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;


public class ShutdownState implements SystemState {
    @Override public String name() { return "Shutdown"; }
    @Override public double consumptionMultiplier() { return 0.0; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("خاموشی کامل (Shutdown): همه سیستم‌ها خاموش هستند.");
    }
}