
package com.prashant.credit.restrictions;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.prashant.credit.data.suite.TestTags;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RestrictionsContractTest extends RestrictionsBase {

    @Test
    @Tag(TestTags.CONTRACT)
    @DisplayName("Should validate the restrictions schema for GET method in v1")
    void contractOnV1() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }

    /*
     * We are overriding the basePath to v2 in the test first line
     *
     * You can enable the test (setting true) and see the errors because the response body is
     * different on v2
     */
    @Test
    @Tag(TestTags.CONTRACT)
    @Disabled("disabled to execute the v1")
    @DisplayName("Should validate the restrictions schema for GET method in v2")
    void contractOnV2() {
        basePath = "/api/v2";

        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }
}
