package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;


public class ActiveState implements SystemState {
    @Override public String name() { return "Active"; }
    @Override public double consumptionMultiplier() { return 1.0; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("سیستم در حالت Active: همه‌ی سیستم‌ها روشن هستند.");
    }
}