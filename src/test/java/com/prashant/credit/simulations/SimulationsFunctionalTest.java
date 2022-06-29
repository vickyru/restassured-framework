
package com.prashant.credit.simulations;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.prashant.credit.commons.MessageFormat;
import com.prashant.credit.model.Simulation;
import com.prashant.credit.data.suite.TestTags;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SimulationsFunctionalTest extends SimulationsBase {

    private static final String FAILED_VALIDATION =
        "com.prashant.credit.data.provider.SimulationDataProvider#failedValidations";

    /*
     * not that, in order to assert the amount without problem, we must enable a configuration
     * it's located at BaseAPI class
     */
    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should validate one existing simulation")
    void getOneExistingSimulation() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            statusCode(SC_OK).
            body(
                "name", equalTo(existingSimulation.getName()),
                "cpf", equalTo(existingSimulation.getCpf()),
                "email", equalTo(existingSimulation.getEmail()),
                "amount", equalTo(existingSimulation.getAmount()),
                "installments", equalTo(existingSimulation.getInstallments()),
                "insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should validate all existing simulations")
    void getAllExistingSimulations() {
        Simulation[] existingSimulations = simulationDataFactory.allExistingSimulations();

        Simulation[] simulationsRequested =
            when().
                get("simulations/").
            then().
                statusCode(SC_OK).
                extract().
                   as(Simulation[].class);

        Assertions.assertThat(existingSimulations).contains(simulationsRequested);
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should filter by name a non-existing simulation")
    void simulationByNameNotFound() {
        given().
            queryParam("name", simulationDataFactory.nonExistentName()).
        when().
            get("/simulations").
        then().
            statusCode(SC_NOT_FOUND);
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should find a simulation filtered by name")
    void returnSimulationByName() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            queryParam("name", existingSimulation.getName()).
        when().
            get("/simulations").
        then().
            statusCode(SC_OK).
            body(
                "[0].name", equalTo(existingSimulation.getName()),
                "[0].cpf", equalTo(existingSimulation.getCpf()),
                "[0].email", equalTo(existingSimulation.getEmail()),
                "[0].amount", equalTo(existingSimulation.getAmount()),
                "[0].installments", equalTo(existingSimulation.getInstallments()),
                "[0].insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    /*
     * here there is an header validation
     */
    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should create a new simulation")
    void createNewSimulationSuccessfully() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulations").
        then().
            statusCode(SC_CREATED).
            header("Location", containsString(MessageFormat.locationURLByEnvironment()));
    }

    @Tag(TestTags.FUNCTIONAL)
    @ParameterizedTest(name = "Scenario: {2}")
    @MethodSource(value = FAILED_VALIDATION)
    @DisplayName("Should validate all the invalid scenarios")
    void invalidSimulations(Simulation invalidSimulation, String path, String validationMessage) {
        given().
            contentType(ContentType.JSON).
            body(invalidSimulation).
        when().
            post("/simulations").
        then().
            statusCode(SC_UNPROCESSABLE_ENTITY).
            body(path, is(validationMessage));
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should validate an CFP duplication")
    void simulationWithDuplicatedCpf() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();
        given().
            contentType(ContentType.JSON).
            body(existingSimulation).
        when().
            post("/simulations/").
        then().
            statusCode(SC_CONFLICT).
            body("message", is("CPF already exists"));
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should delete an existing simulation")
    void deleteSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should validate the return when a non-existent simulation is sent")
    void notFoundWhenDeleteSimulation() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(SC_NOT_FOUND);
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should update an existing simulation")
    void changeSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        Simulation simulation = simulationDataFactory.validSimulation();
        simulation.setCpf(existingSimulation.getCpf());
        simulation.setInsurance(existingSimulation.getInsurance());

        Simulation simulationReturned =
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", existingSimulation.getCpf()).
                body(simulation).
            when().
                put("/simulations/{cpf}").
            then().
                statusCode(SC_OK).
                extract().
                    as(Simulation.class);

        assertThat("Simulation are not the same",
            simulationReturned, is(simulation));
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should validate the return of an update for a non-existent CPF")
    void changeSimulationCpfNotFound() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
            body(simulation).
        when().
            put("/simulations/{cpf}").
        then().
            statusCode(SC_NOT_FOUND);
    }
}
