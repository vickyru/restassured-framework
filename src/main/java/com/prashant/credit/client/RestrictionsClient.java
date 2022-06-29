
package com.prashant.credit.client;

import static io.restassured.RestAssured.given;

import com.prashant.credit.specs.RestrictionsSpecs;
import io.restassured.response.Response;

public class RestrictionsClient {

    public Response queryRestrictionAndReturnNotFound() {
        return
            given().
                spec(RestrictionsSpecs.cpfWithoutRestrictionRequestSpec()).
            when().
                get("/restrictions/{cpf}").
            then().
                spec(RestrictionsSpecs.notFoundResponse()).
                extract().
                    response();
    }
}
