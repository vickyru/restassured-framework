
package com.prashant.credit.e2e;

import com.prashant.credit.client.RestrictionsClient;
import com.prashant.credit.client.SimulationsClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.prashant.credit.data.suite.TestTags.E2E;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class FullSimulationE2ETest {

    private static RestrictionsClient restrictionsClient;
    private static SimulationsClient simulationsClient;

    @BeforeAll
    static void beforeE2e() {
        restrictionsClient = new RestrictionsClient();
        simulationsClient = new SimulationsClient();
    }


    @Test
    @Tag(E2E)
    @DisplayName("Should submit a successful simulation for a non-restricted CPF")
    void completeSimulation() {
        Response notFound = restrictionsClient.queryRestrictionAndReturnNotFound();
        assertThat(notFound.getStatusCode()).isEqualTo(SC_NOT_FOUND);

        Response successfulSimulation = simulationsClient.submitSuccessfulSimulation();
        assertThat(successfulSimulation.getHeader("Location")).isNotEmpty();
    }
}
