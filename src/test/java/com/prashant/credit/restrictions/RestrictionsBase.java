
package com.prashant.credit.restrictions;

import com.prashant.credit.BaseAPI;
import com.prashant.credit.data.factory.RestrictionDataFactory;
import org.junit.jupiter.api.BeforeAll;

public abstract class RestrictionsBase extends BaseAPI {

    protected static RestrictionDataFactory restrictionDataFactory;

    @BeforeAll
    static void setup() {
        restrictionDataFactory = new RestrictionDataFactory();
    }
}
