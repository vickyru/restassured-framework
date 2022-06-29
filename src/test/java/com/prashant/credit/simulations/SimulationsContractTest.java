
package com.prashant.credit.simulations;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.prashant.credit.data.factory.SimulationDataFactory;
import com.prashant.credit.BaseAPI;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimulationsContractTest extends BaseAPI {

    private static SimulationDataFactory simulationDataFactory;

    @BeforeAll
    static void setup() {
        simulationDataFactory = new SimulationDataFactory();
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for GET method")
    void getOneSimulation() {
        String existentCpf = simulationDataFactory.oneExistingSimulation().getCpf();
        given().
            pathParam("cpf", existentCpf).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_v1_schema.json"));
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for non-existing simulation")
    void simulationNotFound() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_not_existent_v1_schema.json"));
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for missing information")
    void simulationWithMissingInformation() {
        given().
            contentType(ContentType.JSON).
            body(simulationDataFactory.missingAllInformation()).
        when().
            post("/simulations").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_missing_info_v1_schema.json"));
    }
}
