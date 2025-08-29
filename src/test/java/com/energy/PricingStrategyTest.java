package com.energy;

import com.energy.model.pricing.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingStrategyTest {

    @Test
    void standardCost() {
        EnergyPricingStrategy s = new StandardPricing();
        // 10 units × 500 = 5000
        assertEquals(5000.0, s.calculate(10), 1e-9);
    }

    @Test
    void peakHoursCost() {
        EnergyPricingStrategy s = new PeakHoursPricing();
        // 10 units × 1000 = 10000
        assertEquals(10000.0, s.calculate(10), 1e-9);
    }

    @Test
    void greenModeCost() {
        EnergyPricingStrategy s = new GreenModePricing();
        // 10 units × 300 = 3000
        assertEquals(3000.0, s.calculate(10), 1e-9);
    }
}
