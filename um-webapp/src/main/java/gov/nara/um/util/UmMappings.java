package gov.nara.um.util;

public final class UmMappings {



    public static final String BUSINESSUNITS = "business-units";
    public static final String BUSINESSUNITS_USERS = "business-units/users";
    //public static final String BUSINESSUNITS_CONFIGURATIONS_PREFERENCES = "business-units/preferences";

    public static final String PERSERVATIONGROUPS = "preservation-groups";
    public static final String PERSERVATIONGROUPS_USERS = "preservation-groups/users";
    //public static final String PERSERVATIONGROUPS_PERMISSIONS = "preservation-groups/permissions";
    public static final String USERS = "users";
    // discoverability

    public static final class Singular {


        public static final String BUSINESSUNIT = "business-unit";
        public static final String PRESERVATIONGROUP = "preservation-group";

    }

    public static final String AUTHENTICATION = "authentication";

    private UmMappings() {
        throw new AssertionError();
    }

    // API




}
