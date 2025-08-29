package com.energy;

import com.energy.model.BuildingEnergySystem;
import com.energy.model.pricing.*;
import com.energy.model.state.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingEnergySystemTest {

    @Test
    void defaultStatus() {
        BuildingEnergySystem sys = new BuildingEnergySystem();
        assertEquals("Standard", sys.getPricing().name());
        assertEquals("Active", sys.getState().name());
    }

    @Test
    void ecoReducesCost() {
        BuildingEnergySystem sys = new BuildingEnergySystem();
        sys.setPricing(new StandardPricing());
        sys.setState(new EcoModeState());
        assertEquals(2000.0, sys.calculateCost(10), 1e-9);
    }

    @Test
    void shutdownZeroCost() {
        BuildingEnergySystem sys = new BuildingEnergySystem();
        sys.setPricing(new PeakHoursPricing());
        sys.setState(new ShutdownState());
        assertEquals(0.0, sys.calculateCost(100), 1e-9);
    }
}
