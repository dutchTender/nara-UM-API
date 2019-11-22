package gov.nara.um.spring.controller;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.AbstractController;
import gov.nara.common.web.controller.ISortingController;
import gov.nara.um.persistence.dto.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.BusinessUnitDTO;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.persistence.model.BusinessUnitConfigurationPreference;
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
@RequestMapping(value = UmMappings.BUSINESSUNITS)
public class BusinessUnitController extends AbstractController<BusinessUnit> implements ISortingController<BusinessUnit> {

    @Autowired
    private IBusinessUnitService service;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnit> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // sort helper
    public List<BusinessUnit> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return findPaginatedInternal(page, size);
    }



    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllPaginatedBU(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {



        List<BusinessUnitDTO> returnList =  new ArrayList<>();
        List<BusinessUnit> rawResultsList =  findPaginatedInternal(page, size);
        for(Iterator<BusinessUnit> iterBU = rawResultsList.listIterator(); iterBU.hasNext(); ) {

            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
            businessUnitDTO.setId(currentBU.getId());
            businessUnitDTO.setName(currentBU.getName());
            businessUnitDTO.setOrg_code(currentBU.getOrg_code());
            businessUnitDTO.setLdap_name(currentBU.getLdapName());
            List<BusinessUnitConfigurationPreference> preferenceList = currentBU.getBusinessUnitConfigurationPreferences();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = preferenceList.listIterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = new BusinessUnitConfigPreferenceDTO();
                businessUnitConfigPreferenceDTO.setBusiness_unit_id(currentBUCP.getBusinessUnitID().getId());
                businessUnitConfigPreferenceDTO.setBusiness_unit_config_id(currentBUCP.getBusinessUnitConfigID().getId());
                businessUnitConfigPreferenceDTO.setConfiguration_value(currentBUCP.getConfigurationValue());

                businessUnitDTO.addBusinessUnitConfigPreference(businessUnitConfigPreferenceDTO);


            }
            returnList.add(businessUnitDTO);

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

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnit> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<BusinessUnit> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }



    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllBU(final HttpServletRequest request) {

        List<BusinessUnitDTO> returnList =  new ArrayList<>();
        List<BusinessUnit> rawResultsList =  findAllInternal(request);
        for(Iterator<BusinessUnit> iterBU = rawResultsList.listIterator(); iterBU.hasNext(); ) {

            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
            businessUnitDTO.setId(currentBU.getId());
            businessUnitDTO.setName(currentBU.getName());
            businessUnitDTO.setOrg_code(currentBU.getOrg_code());
            businessUnitDTO.setLdap_name(currentBU.getLdapName());
            List<BusinessUnitConfigurationPreference> preferenceList = currentBU.getBusinessUnitConfigurationPreferences();
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = preferenceList.listIterator(); iterBUCP.hasNext(); ) {
                BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = new BusinessUnitConfigPreferenceDTO();
                businessUnitConfigPreferenceDTO.setBusiness_unit_id(currentBUCP.getBusinessUnitID().getId());
                businessUnitConfigPreferenceDTO.setBusiness_unit_config_id(currentBUCP.getBusinessUnitConfigID().getId());
                businessUnitConfigPreferenceDTO.setConfiguration_value(currentBUCP.getConfigurationValue());

                businessUnitDTO.addBusinessUnitConfigPreference(businessUnitConfigPreferenceDTO);


            }
            returnList.add(businessUnitDTO);

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
    public BusinessUnit findOne(@PathVariable("id") final Integer id) {
        return findOneInternal(id);
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
    public void create(@RequestBody final BusinessUnit resource) {

        createInternal(resource);
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
    public void update(@PathVariable("id") final Integer id, @RequestBody final BusinessUnit resource) {
        updateInternal(id, resource);
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
    public void delete(@PathVariable("id") final Integer id) {
        deleteByIdInternal(id);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Spring
    // dependency injection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected final IBusinessUnitService getService() {
        return service;
    }

}
