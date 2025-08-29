package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;
import com.energy.model.EnergyConstants;


public class ActiveState implements SystemState {
    @Override public String name() { return "Active"; }
    @Override public double consumptionMultiplier() { return EnergyConstants.MUL_ACTIVE; }


    @Override public void onEnter(BuildingEnergySystem ctx) {
        ctx.log("سیستم در حالت Active: همه‌ی سیستم‌ها روشن هستند.");
    }
}