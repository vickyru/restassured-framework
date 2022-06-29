
package com.prashant.credit.specs;

import static org.hamcrest.CoreMatchers.startsWith;

import com.prashant.credit.commons.MessageFormat;
import com.prashant.credit.data.factory.SimulationDataFactory;
import com.prashant.credit.model.Simulation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class SimulationsSpecs {

    private SimulationsSpecs() {}

    public static RequestSpecification postValidSimulation() {
        Simulation validSimulation = new SimulationDataFactory().validSimulation();

        return new RequestSpecBuilder().
            addRequestSpecification(InitialStateSpecs.set()).
            setContentType(ContentType.JSON).
            setBody(validSimulation).
            build();
    }

    public static ResponseSpecification createdSimulation() {
        return new ResponseSpecBuilder().
            expectStatusCode(HttpStatus.SC_CREATED).
            expectHeader("Location", startsWith(MessageFormat.locationURLByEnvironment())).
            build();
    }
}
