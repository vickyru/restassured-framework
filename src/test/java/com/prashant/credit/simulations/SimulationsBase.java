
package com.prashant.credit.simulations;

import com.prashant.credit.BaseAPI;
import com.prashant.credit.data.factory.SimulationDataFactory;
import org.junit.jupiter.api.BeforeAll;

public abstract class SimulationsBase extends BaseAPI {

    protected static SimulationDataFactory simulationDataFactory;

    @BeforeAll
    static void setup() {
        simulationDataFactory = new SimulationDataFactory();
    }
}
