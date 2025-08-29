package com.energy;


import com.energy.model.BuildingEnergySystem;
import com.energy.util.ConsoleUI;


public class App {
    public static void main(String[] args) {
        var system = new BuildingEnergySystem();
        var ui = new ConsoleUI(system);
        ui.run();
    }
}