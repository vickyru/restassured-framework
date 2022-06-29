
package com.prashant.credit.commons;

import com.prashant.credit.config.Configuration;
import com.prashant.credit.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageFormat {

    private static final Logger log = LogManager.getLogger(MessageFormat.class);

    private MessageFormat() {}
    /*
     * This method was created to remove the post if the environment is test because the 443 port must be informed
     * to make the requests, but should not be show in the URL
     */
    public static String locationURLByEnvironment() {
        String locationURL;
        Configuration configuration = ConfigurationManager.getConfiguration();

        locationURL = configuration.port() < 8000 ? java.text.MessageFormat
            .format("{0}{1}/simulations/", configuration.baseURI(), configuration.basePath())
            : java.text.MessageFormat.format("{0}:{1}{2}/simulations/", configuration.baseURI(),
                String.valueOf(configuration.port()), configuration.basePath());

        log.debug(locationURL);
        return locationURL;
    }
}
