package gov.nara.um.util;

public final class UmMappings {



    public static final String BUSINESSUNITS = "business-units";

    public static final String USERS = "users";

    public static final String USERS_BUSINESSUNITS = "users/business-units";

    public static final String BUSINESSUNITS_USERS = "/business-units/users";


    public static final String BUSINESSUNITS_CONFIGURATIONS = "/business-units/businessunits-configurations";

    public static final String BUSINESSUNITS_CONFIGURATIONS_PREFERENCES = "/business-units/businessunits-config-preferences";
    // discoverability

    public static final class Singular {


        public static final String BUSINESSUNIT = "business-unit";

        public static final String USER = "user";
    }

    public static final String AUTHENTICATION = "authentication";

    private UmMappings() {
        throw new AssertionError();
    }

    // API




}
