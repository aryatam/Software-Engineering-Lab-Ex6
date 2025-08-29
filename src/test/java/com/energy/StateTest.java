package com.energy;

import com.energy.model.state.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTest {

    @Test
    void multipliers() {
        assertEquals(1.0, new ActiveState().consumptionMultiplier(), 1e-9);
        assertEquals(0.4, new EcoModeState().consumptionMultiplier(), 1e-9);
        assertEquals(0.0, new ShutdownState().consumptionMultiplier(), 1e-9);
    }
}
