package com.energy;

import com.energy.model.BuildingEnergySystem;
import com.energy.service.EnergyFacade;
import com.energy.util.ConsoleUI;

public class App {
    public static void main(String[] args) {
        var system = new BuildingEnergySystem();
        var facade = new EnergyFacade(system);
        var ui = new ConsoleUI(facade);
        ui.run();
    }
}
