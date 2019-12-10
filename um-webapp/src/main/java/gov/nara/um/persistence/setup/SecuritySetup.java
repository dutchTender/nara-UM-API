package gov.nara.um.persistence.setup;

import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.service.bussinessunits.IBusinessUnitService;
import gov.nara.um.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import gov.nara.common.spring.util.Profiles;

/**
 * This simple setup class will run during the bootstrap process of Spring and will create some setup data <br>
 * The main focus here is creating some standard privileges, then roles and finally some default principals/users
 */
@Component
@Profile(Profiles.DEPLOYED)
public class SecuritySetup implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LoggerFactory.getLogger(SecuritySetup.class);

    private boolean setupDone;


    @Autowired
    private  IBusinessUnitService iBusinessUnitService;

    @Autowired
    private  IUserService iUserService;


    public SecuritySetup() {
        super();
    }

    //

    /**
     * - note that this is a compromise - the flag makes this bean statefull which can (and will) be avoided in the future by a more advanced mechanism <br>
     * - the reason for this is that the context is refreshed more than once throughout the lifecycle of the deployable <br>
     * - alternatives: proper persisted versioning
     */
    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!setupDone) {
            logger.info("Executing Setup");

             CrateUser();




            setupDone = true;
            logger.info("Setup Done");
        }
    }

    // Privilege

    private void CrateUser(){

        User user = new User();
        user.setName("Li.zhang");
        user.setUser_type("ERA");

        iUserService.create(user);

        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("ADIS");
        businessUnit.setOrg_code("NARA");
        businessUnit.setLdapName("ADIS");
        iBusinessUnitService.create(businessUnit);



        User user2 = iUserService.findByName(user.getName());


        user2.addBusinessUnit(businessUnit);
        iUserService.update(user2);


    }

    // Role



}