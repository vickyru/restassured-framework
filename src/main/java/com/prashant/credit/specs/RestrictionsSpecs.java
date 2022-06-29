
package com.prashant.credit.specs;

import com.prashant.credit.data.factory.RestrictionDataFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class RestrictionsSpecs {

    private RestrictionsSpecs() {
    }

    public static RequestSpecification cpfWithoutRestrictionRequestSpec() {
        RestrictionDataFactory restrictionDataFactory = new RestrictionDataFactory();

        return new RequestSpecBuilder().
            addRequestSpecification(InitialStateSpecs.set()).
            addPathParam("cpf", restrictionDataFactory.cpfWithoutRestriction()).
            build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder().
            expectStatusCode(HttpStatus.SC_NOT_FOUND).
            build();
    }

}
