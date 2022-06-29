
package com.prashant.credit.restrictions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import java.text.MessageFormat;

import com.prashant.credit.data.suite.TestTags;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RestrictionsFunctionalTest extends RestrictionsBase {

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should query a CPF without restriction")
    void cpfWithNoRestriction() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithoutRestriction()).
        when().
            get("/restrictions/{cpf}").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Tag(TestTags.FUNCTIONAL)
    @DisplayName("Should query a CPF with restriction")
    void cpfWithRestriction() {
        String cpfWithRestriction = restrictionDataFactory.cpfWithRestriction();

        given().
            pathParam("cpf", cpfWithRestriction).
        when().
            get("/restrictions/{cpf}").
        then()
            .statusCode(HttpStatus.SC_OK).
            body("message",
                is(MessageFormat.format("CPF {0} has a restriction", cpfWithRestriction)));
    }
}
