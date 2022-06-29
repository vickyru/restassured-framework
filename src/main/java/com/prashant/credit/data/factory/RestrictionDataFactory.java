
package com.prashant.credit.data.factory;

import com.github.javafaker.Faker;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestrictionDataFactory {

    private final Faker faker;
    private static final Logger log = LogManager.getLogger(RestrictionDataFactory.class);

    public RestrictionDataFactory() {
        faker = new Faker();
    }

    public String cpfWithoutRestriction() {
        return String.valueOf(
            faker.number().randomNumber(11, false));
    }

    public String cpfWithRestriction() {
        List<String> cpfWithRestriction =
            Arrays.asList(
                "97093236014",
                "60094146012",
                "84809766080",
                "62648716050",
                "26276298085",
                "01317496094",
                "55856777050",
                "19626829001",
                "24094592008",
                "58063164083"
            );

        String randomCpf = cpfWithRestriction.get(new Random().nextInt(cpfWithRestriction.size()));

        log.debug(randomCpf);
        return randomCpf;
    }
}
