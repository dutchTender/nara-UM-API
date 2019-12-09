package gov.nara.um.spring.web.businessunits;



import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferencesPutDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfigurationPreference;
import gov.nara.um.util.UmMappings;
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
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
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
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
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
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, resource);
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
    public void update(@PathVariable("id") final Integer id, @RequestBody final BusinessUnitConfigPreferencesPutDTO resource) {

        BusinessUnit businessUnit = getService().findOne(id);

        if(businessUnit == null){
            throw new MyBadRequestException("provided path id variable is not valid. Bad Request exception.");
        }


        List<BusinessUnitConfigPreferenceDTO> inputPrefListDTO = resource.getBusinessUnitConfigPreferences();
        List<BusinessUnitConfigurationPreference> preferencesList = businessUnit.getBusinessUnitConfigurationPreferences();
        if(inputPrefListDTO.size() > 0) { // input preferences is not null

            if(preferencesList.size() > 0){  // existing preferences is not empty

                for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCPDTO = inputPrefListDTO.listIterator(); iterBUCPDTO.hasNext();){
                    BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = iterBUCPDTO.next();
                    Integer bucpIndex = 0;
                    Integer currentPreferenceSize = businessUnit.getBusinessUnitConfigurationPreferences().size();
                    for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = businessUnit.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                        // check if preferences that needs update

                        if(bucpIndex <= currentPreferenceSize){
                            // verify that the DTO ids match the actual object
                            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = iterBUCP.next();

                            // business unit ids is implicitly supplied ..only need to verify config idp
                            if(businessUnitConfigurationPreference.getBusinessUnitConfigID().getId() == businessUnitConfigPreferenceDTO.getBusiness_unit_config_id()){
                                businessUnitConfigurationPreference.setConfigurationValue(businessUnitConfigPreferenceDTO.getConfiguration_value());
                                businessUnit.getBusinessUnitConfigurationPreferences().set(bucpIndex, businessUnitConfigurationPreference);
                            }
                            else{
                                throw new MyBadRequestException("only configuration value is allowed to be updated. not the configuration id key");
                            }

                        }
                        else {

                            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, businessUnitConfigPreferenceDTO);
                            businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);

                        }

                        bucpIndex++;


                    }

                }
            }
            else {
                // existing preferences empty. just need to add new preferences
                // create busienss preference and add it to business unit
                for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCPDTO = inputPrefListDTO.listIterator(); iterBUCPDTO.hasNext();){
                    BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = iterBUCPDTO.next();
                    BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, businessUnitConfigPreferenceDTO);
                    businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
                }

            }

            getService().update(businessUnit);

        }
        else {
            // input list is null ..clear all preferences
            businessUnit.getBusinessUnitConfigurationPreferences().clear();
            getService().update(businessUnit);
        }

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
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, resource);
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

