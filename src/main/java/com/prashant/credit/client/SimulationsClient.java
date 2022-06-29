
package com.prashant.credit.client;

import static io.restassured.RestAssured.given;

import com.prashant.credit.specs.SimulationsSpecs;
import io.restassured.response.Response;

public class SimulationsClient {

    public Response submitSuccessfulSimulation() {
        return
            given().
                spec(SimulationsSpecs.postValidSimulation()).
            when().
                post("/simulations").
            then().
                spec(SimulationsSpecs.createdSimulation()).
                extract().
                    response();
    }
}
