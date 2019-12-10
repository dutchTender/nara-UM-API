package gov.nara.um.spring.web.businessunits;



import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfigurationPreference;
import gov.nara.um.util.UmMappings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitConfigPreferenceDTO> findAllPaginatedAndSortedBUDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                                @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<BusinessUnitConfigPreferenceDTO> returnList = new ArrayList<>();
        for(Iterator<BusinessUnit> iterBU =  findPaginatedAndSortedInternal(page, size, sortBy, sortOrder).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
                returnList.add(businessUnitConfigPreferenceDTO);
            }
        }
        return returnList;
    }




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
        for(Iterator<BusinessUnit> iterBU = getService().findAll().listIterator(); iterBU.hasNext(); ) {
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
    public void create(@RequestBody final List<BusinessUnitConfigPreferenceDTO> resource) {
        BusinessUnit businessUnit = getService().findOne(resource.get(0).getBusiness_unit_id());
        for (Iterator<BusinessUnitConfigPreferenceDTO> iterBUCPDTO = resource.iterator(); iterBUCPDTO.hasNext(); ) {
            BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = iterBUCPDTO.next();

            if(businessUnit != null){
                BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, businessUnitConfigPreferenceDTO);
                businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            }
            else{
                throw new MyResourceNotFoundException("the payload id for business unit is not valid.");
            }

        }
        getService().update(businessUnit);



       // createInternal(resource);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // update - one
    // Unit testing  : NA
    // Integration testing : NA
    // this should have same behavior as the
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Integer id, @RequestBody final List<BusinessUnitConfigPreferenceDTO> resource) {

        BusinessUnit businessUnit = getService().findOne(id);
        if(businessUnit == null){
            throw new MyBadRequestException("provided path id variable is not valid. Bad Request exception.");
        }

        // clear out current relationships
        businessUnit.getBusinessUnitConfigurationPreferences().clear();
        getService().update(businessUnit);
        for (Iterator<BusinessUnitConfigPreferenceDTO> iterBUCPDTO = resource.iterator(); iterBUCPDTO.hasNext(); ) {
            BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, iterBUCPDTO.next());
            businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
        }
        getService().update(businessUnit);

        // iterate over resource and add new relationships

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

