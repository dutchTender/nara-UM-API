package gov.nara.um.spring.controller;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.AbstractController;
import gov.nara.common.web.controller.ISortingController;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.BusinessUnitDTO;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.persistence.model.BusinessUnitConfiguration;
import gov.nara.um.persistence.model.BusinessUnitConfigurationPreference;
import gov.nara.um.service.IBusinessUnitConfigurationService;
import gov.nara.um.service.IBusinessUnitService;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




@Controller
@RequestMapping(value = UmMappings.BUSINESSUNITS)
public class BusinessUnitController extends AbstractController<BusinessUnit> implements ISortingController<BusinessUnit> {

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

   @Override
    public List<BusinessUnit> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return findPaginatedInternal(page, size);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllPaginatedBUDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<BusinessUnitDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findPaginatedInternal(page,size).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO =buildBusinessUnitDTO(currentBU);
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(currentBUCP);
                businessUnitDTO.addBusinessUnitConfigPreferenceDTO(businessUnitConfigPreferenceDTO);
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
    public List<BusinessUnit> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }


    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllSortedBUDTO(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        List<BusinessUnitDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findAllSortedInternal(sortBy, sortOrder).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(currentBU);
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(currentBUCP);
                businessUnitDTO.addBusinessUnitConfigPreferenceDTO(businessUnitConfigPreferenceDTO);
            }
            returnList.add(businessUnitDTO);

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
    @Override
    public List<BusinessUnit> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }



    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllBUDTO(final HttpServletRequest request) {
        List<BusinessUnitDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findAllInternal(request).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(currentBU);
            for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO( currentBUCP);
                businessUnitDTO.addBusinessUnitConfigPreferenceDTO(businessUnitConfigPreferenceDTO);
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
    public BusinessUnitDTO findOne(@PathVariable("id") final Integer id) {

        BusinessUnit currentBU = findOneInternal(id);
        BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(currentBU);
        for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
            BusinessUnitConfigurationPreference currentBUCP = iterBUCP.next();
            BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(currentBUCP);
            businessUnitDTO.addBusinessUnitConfigPreferenceDTO(businessUnitConfigPreferenceDTO);
        }

        return businessUnitDTO;
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
    @Transactional
    public void create(@RequestBody final BusinessUnitDTO resource) {

        // validate DTO
        // we need to do some manual checks here
        // name has to be there
        // name has to be unique



        // assumes DTO is valid
        // build business unit object
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName(resource.getName());
        businessUnit.setOrg_code(resource.getOrg_code());
        businessUnit.setLdapName(resource.getLdapName());
        createInternal(businessUnit);

        // business unit preference is required ....can be empty
        // if preference is not null in the DTO
        List<BusinessUnitConfigPreferenceDTO> prefList = resource.getBusinessUnitConfigPreferences();
        if(prefList.size() > 0){ // we may force this size to be 1
            BusinessUnit currentBU = getService().findByName(resource.getName()); // this should
            if(currentBU == null){
                throw  new MyResourceNotFoundException("Business unit could not be found. could not attach preference.");
            }
            for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCP = prefList.listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = iterBUCP.next();
                BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
                businessUnitConfigurationPreference.setBusinessUnitID(currentBU);
                businessUnitConfigurationPreference.setBusinessUnitConfigID(configurationService.findOne(businessUnitConfigPreferenceDTO.getBusiness_unit_config_id()));
                businessUnitConfigurationPreference.setConfigurationValue(businessUnitConfigPreferenceDTO.getConfiguration_value());
                currentBU.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            }
            service.update(currentBU);
        }

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


    // private helpers

    private BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setId(currentBU.getId());
        businessUnitDTO.setName(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getOrg_code());
        businessUnitDTO.setLdapName(currentBU.getLdapName());

        return businessUnitDTO;
    }

    private BusinessUnitConfigPreferenceDTO buildBusinessConfigPreferenceDTO(BusinessUnitConfigurationPreference currentBUCP){
        BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = new BusinessUnitConfigPreferenceDTO();
        businessUnitConfigPreferenceDTO.setBusiness_unit_id(currentBUCP.getBusinessUnitID().getId());
        businessUnitConfigPreferenceDTO.setBusiness_unit_config_id(currentBUCP.getBusinessUnitConfigID().getId());
        businessUnitConfigPreferenceDTO.setConfiguration_value(currentBUCP.getConfigurationValue());
        return businessUnitConfigPreferenceDTO;
    }



}
