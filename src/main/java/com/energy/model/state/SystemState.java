package com.energy.model.state;


import com.energy.model.BuildingEnergySystem;


public interface SystemState {
    String name();
    double consumptionMultiplier();


    default void onEnter(BuildingEnergySystem ctx) {}
    default void onExit(BuildingEnergySystem ctx) {}
}