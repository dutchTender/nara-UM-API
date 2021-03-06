package gov.nara.um.spring.web.businessunits;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.ISortingController;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyConflictException;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
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
@RequestMapping(value = UmMappings.BUSINESSUNITS)
public class BusinessUnitController extends BusinessUnitBaseController implements ISortingController<BusinessUnit> {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override

    public List<BusinessUnit> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }



    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllPaginatedAndSortedBUDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                        @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<BusinessUnitDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = findPaginatedAndSortedInternal(page, size, sortBy, sortOrder).listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(currentBU);
            returnList.add(businessUnitDTO);

        }

        return returnList;
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
            BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(iterBU.next());
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
        List<BusinessUnitDTO> returnList = buildBuindessUnitDTOList( findAllSortedInternal(sortBy, sortOrder));
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


    @CrossOrigin(origins = "http://localhost:63342")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessUnitDTO> findAllBUDTO(final HttpServletRequest request) {
        List<BusinessUnitDTO> returnList = buildBuindessUnitDTOList(findAllInternal(request));
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
    public void create(@RequestBody final BusinessUnitDTO resource) {

        // validate DTO
        // we need to do some manual checks here
        // name has to be there
        // name has to be unique

        // verify that they new name does not clash with existing business unit names
        BusinessUnit uniqueUnit = getService().findByName(resource.getBusiness_unit_name());
        if(uniqueUnit != null){
            throw new MyConflictException("there is already a business unit with that business name. Data integrity exception.");
        }

        uniqueUnit = getService().findByCode(resource.getOrg_code());

        if (uniqueUnit != null) {
                throw new MyConflictException("there is already a business unit with that org code. Data integrity exception.");
        }

        // assumes DTO is valid
        // build business unit object
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName(resource.getBusiness_unit_name());
        businessUnit.setCode(resource.getOrg_code());
        businessUnit.setLdapName(resource.getLdap_name());
        createInternal(businessUnit);

        // business unit preference is required ....can be empty
        // if preference is not null in the DTO
        List<BusinessUnitConfigPreferenceDTO> prefList = resource.getBusiness_unit_Preferences();

        if(prefList.size() > 0){ // we may force this size to be 1
            BusinessUnit currentBU = getService().findByName(resource.getBusiness_unit_name()); // this should
            for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCP = prefList.listIterator(); iterBUCP.hasNext();){
                BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, iterBUCP.next());
                currentBU.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            }
            getService().update(currentBU);
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
    public void update(@PathVariable("id") final Integer id, @RequestBody final BusinessUnitDTO resource) {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        // validate DTO
        // we need to do some manual checks here
        // name has to be there
        // name has to be unique
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        // verify that the name field is unique
        // verify that they new name does not clash with existing business unit names
        BusinessUnit uniqueUnit = getService().findByName(resource.getBusiness_unit_name());
        if(uniqueUnit.getId() != id){
            throw new MyConflictException("there is already a business unit with that business name. Data integrity exception.");
        }

        uniqueUnit = getService().findByCode(resource.getOrg_code());

        if (uniqueUnit != null) {
           if (uniqueUnit.getId() != id) {
               throw new MyConflictException("there is already a business unit with that org code. Data integrity exception.");
           }

       }


        BusinessUnit businessUnit = getService().findOne(id);
        if(businessUnit == null){
            throw new MyBadRequestException("provided path id variable is not valid. Bad Request exception.");
        }


        // assumes DTO is valid when execution reaches here
        // build business unit object
        businessUnit.setName(resource.getBusiness_unit_name());
        businessUnit.setCode(resource.getOrg_code());
        businessUnit.setLdapName(resource.getLdap_name());



        List<BusinessUnitConfigPreferenceDTO> prefListDTO = resource.getBusiness_unit_Preferences();
        List<BusinessUnitConfigurationPreference> preferencesList = businessUnit.getBusinessUnitConfigurationPreferences();
        if(prefListDTO.size() > 0) { // input preferences is not null

                businessUnit.getBusinessUnitConfigurationPreferences().clear();
                // existing preferences empty. just need to add new preferences
                // create business preference and add it to business unit
                getService().update(businessUnit);
                for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCPDTO = prefListDTO.listIterator(); iterBUCPDTO.hasNext();){
                    BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = iterBUCPDTO.next();
                    BusinessUnitConfigurationPreference businessUnitConfigurationPreference = buildBusinessUnitConfigurationPreference(businessUnit, businessUnitConfigPreferenceDTO);
                    businessUnit.addBusinessUnitConfigurationPreference(businessUnitConfigurationPreference);
            }

            getService().update(businessUnit);

        }
        else {
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
    public void delete(@PathVariable("id") final Integer id) {
        deleteByIdInternal(id);
    }




    // private helpers





}
