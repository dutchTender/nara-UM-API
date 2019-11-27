package gov.nara.um.spring.controller;



import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.BusinessUnitDTO;
import gov.nara.um.persistence.model.*;
import gov.nara.um.service.IBusinessUnitConfigurationService;
import gov.nara.um.service.IBusinessUnitService;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping(value = UmMappings.BUSINESSUNITS_CONFIGURATIONS_PREFERENCES)
public class BusinessUnitConfigurationPreferenceController extends BusinessUnitBaseController {




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
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigPreferenceDTO> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<BusinessUnitConfigPreferenceDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findPaginatedInternal(page,size).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
                returnList.add(businessUnitConfigPreferenceDTO);
            }
        }

        return returnList;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/sorted
    // Unit testing  : DONE
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigPreferenceDTO> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        List<BusinessUnitConfigPreferenceDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findAllSortedInternal(sortBy, sortOrder).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();

            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
                returnList.add(businessUnitConfigPreferenceDTO);
            }

        }

        return returnList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigPreferenceDTO> findAll(final HttpServletRequest request) {
        List<BusinessUnitConfigPreferenceDTO> returnList = new ArrayList<>();

        List<BusinessUnit>  buList = getService().findAll();
        // build return list by looping through all users
        for(Iterator<BusinessUnit> iterUser = buList.iterator(); iterUser.hasNext(); ) {
            BusinessUnit current = iterUser.next();
            List<BusinessUnitConfigurationPreference> preferenceList = current.getBusinessUnitConfigurationPreferences();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = preferenceList.iterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference bucp = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(bucp);
                returnList.add(businessUnitConfigPreferenceDTO);
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
    public List<BusinessUnitConfigPreferenceDTO> findOne(@PathVariable("id") final Integer id) {
        List<BusinessUnitConfigPreferenceDTO> returnList = new ArrayList<>();
        BusinessUnit businessUnit = getService().findOne(id);
        // build return list by looping through all users
        if( businessUnit != null) {
            for (Iterator<BusinessUnitConfigurationPreference> iterBUCP = businessUnit.getBusinessUnitConfigurationPreferences().iterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference bucp = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(bucp);
                returnList.add(businessUnitConfigPreferenceDTO);
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

        BusinessUnit businessUnit = getService().findOne(resource.getBusiness_unit_id());
        if(businessUnit != null){
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
            businessUnitConfigurationPreference.setBusinessUnitID(businessUnit);
            BusinessUnitConfiguration businessUnitConfiguration = getConfigurationService().findOne(resource.getBusiness_unit_config_id());
            businessUnitConfigurationPreference.setBusinessUnitConfigID(businessUnitConfiguration);
            businessUnitConfigurationPreference.setConfigurationValue(resource.getConfiguration_value());
            businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            getService().update(businessUnit);
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

        BusinessUnit businessUnit = getService().findOne(resource.getBusiness_unit_id());
        if(businessUnit != null){
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
            businessUnitConfigurationPreference.setBusinessUnitID(businessUnit);
            BusinessUnitConfiguration businessUnitConfiguration = getConfigurationService().findOne(resource.getBusiness_unit_config_id());
            businessUnitConfigurationPreference.setBusinessUnitConfigID(businessUnitConfiguration);
            businessUnitConfigurationPreference.setConfigurationValue(resource.getConfiguration_value());
            businessUnit.removeBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            getService().update(businessUnit);
        }
        else {
            throw new MyResourceNotFoundException("the payload id for business unit is not valid.");
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Spring
    // dependency injection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

