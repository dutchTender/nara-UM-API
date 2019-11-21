package gov.nara.um.spring.controller;



import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.AbstractController;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.controller.ISortingController;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.model.*;
import gov.nara.um.service.IBusinessUnitConfigurationService;
import gov.nara.um.service.IBusinessUnitService;
import gov.nara.um.service.IUserService;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = UmMappings.BUSINESSUNITS_CONFIGURATIONS_PREFERENCES)
public class BusinessUnitConfigurationPreferenceController  {

    @Autowired
    private IBusinessUnitService service;


    @Autowired
    private IBusinessUnitConfigurationService configurationService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/sorted
    // Unit testing  : DONE
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigurationPreference> findAll(final HttpServletRequest request) {
        List<BusinessUnitConfigurationPreference> returnList = new ArrayList<BusinessUnitConfigurationPreference>();
        List<BusinessUnit>  buList = service.findAll();
        // build return list by looping through all users
        for(Iterator<BusinessUnit> iterUser = buList.iterator(); iterUser.hasNext(); ) {
            BusinessUnit current = iterUser.next();
            List<BusinessUnitConfigurationPreference> preferenceList = current.getBusinessUnitConfigurationPreferences();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = preferenceList.iterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference bucp = iterBUCP.next();
                returnList.add(bucp);
            }

        }
        return returnList;


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigurationPreference> findOne(@PathVariable("id") final Integer id) {
        List<BusinessUnitConfigurationPreference> returnList = new ArrayList<BusinessUnitConfigurationPreference>();
        BusinessUnit businessUnit = service.findOne(id);
        // build return list by looping through all users
        if( businessUnit != null) {
            for (Iterator<BusinessUnitConfigurationPreference> iterBUCP = businessUnit.getBusinessUnitConfigurationPreferences().iterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference bucp = iterBUCP.next();
                returnList.add(bucp);
            }

        }
        else {
            throw new MyResourceNotFoundException("the path id for business unit is not valid.");
        }
        return returnList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final BusinessUnitConfigPreferenceDTO resource) {

        BusinessUnit businessUnit = service.findOne(resource.getBusiness_unit_id());
        if(businessUnit != null){
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
            businessUnitConfigurationPreference.setBusinessUnitID(businessUnit);
            BusinessUnitConfiguration businessUnitConfiguration = configurationService.findOne(resource.getBusiness_unit_config_id());
            businessUnitConfigurationPreference.setBusinessUnitConfigID(businessUnitConfiguration);
            businessUnitConfigurationPreference.setConfigurationValue(resource.getConfiguration_value());
            businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            service.update(businessUnit);
        }
        else {
            throw new MyResourceNotFoundException("the payload id for business unit is not valid.");
        }

       // createInternal(resource);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // update - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody final BusinessUnitConfigurationPreference resource) {
        //updateInternal(id, resource);

        // not really sure we can do update here ???
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Delete - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id, @RequestBody final BusinessUnitConfigPreferenceDTO resource) {
        //deleteByIdInternal(id);

        BusinessUnit businessUnit = service.findOne(resource.getBusiness_unit_id());
        if(businessUnit != null){
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
            businessUnitConfigurationPreference.setBusinessUnitID(businessUnit);
            BusinessUnitConfiguration businessUnitConfiguration = configurationService.findOne(resource.getBusiness_unit_config_id());
            businessUnitConfigurationPreference.setBusinessUnitConfigID(businessUnitConfiguration);
            businessUnitConfigurationPreference.setConfigurationValue(resource.getConfiguration_value());
            businessUnit.removeBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            service.update(businessUnit);
        }
        else {
            throw new MyResourceNotFoundException("the payload id for business unit is not valid.");
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Spring
    // dependency injection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected final IBusinessUnitService getService() {
        return service;
    }

}

