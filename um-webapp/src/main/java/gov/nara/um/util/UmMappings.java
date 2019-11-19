package gov.nara.um.util;

public final class UmMappings {



    public static final String BUSINESSUNITS = "businessunits";

    public static final String USERS = "users";

    public static final String USERS_BUSINESSUNITS = "users/businessunits";
    // discoverability

    public static final class Singular {


        public static final String BUSINESSUNIT = "businessunit";

        public static final String USER = "user";
    }

    public static final String AUTHENTICATION = "authentication";

    private UmMappings() {
        throw new AssertionError();
    }

    // API

}
