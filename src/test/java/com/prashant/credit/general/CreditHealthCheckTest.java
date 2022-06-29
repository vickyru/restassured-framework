
package com.prashant.credit.general;

import static com.prashant.credit.data.suite.TestTags.HEALTH;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

import com.prashant.credit.config.ConfigurationManager;
import com.prashant.credit.BaseAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreditHealthCheckTest extends BaseAPI {

    @BeforeEach
    public void setup() {
        configuration = ConfigurationManager.getConfiguration();
        basePath = configuration.health();
    }

    @AfterEach
    void tearDown() {
        basePath = configuration.basePath();
    }

    @Test
    @Tag(HEALTH)
    @DisplayName("Should be able to hit the health endpoint")
    void healthCheck() {
        when().
            get("/health").
        then().
            statusCode(SC_OK).
            body("status", is("UP"));
    }
}
