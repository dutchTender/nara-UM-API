package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupPermissionDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfigurationPreference;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
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
@RequestMapping(value = UmMappings.PERSERVATIONGROUPS_PERMISSIONS)
public class PreservationGroupPermissionsController extends PreservationGroupBaseController {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<PreservationGroupPermissionDTO> findAllPaginatedAndSortedBUDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                                               @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<PreservationGroupPermissionDTO> returnList = new ArrayList<>();
        for(Iterator<PreservationGroup> iterPG = findPaginatedAndSortedInternal(page, size, sortBy, sortOrder).listIterator(); iterPG.hasNext(); ) {
            PreservationGroup currentPG = iterPG.next();
            for(Iterator<PreservationGroupPermission> iterBUCP = currentPG.getInheritedGroups().listIterator(); iterBUCP.hasNext();){
                PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO(iterBUCP.next());
                returnList.add(preservationGroupPermissionDTO);
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
    public List<PreservationGroupPermissionDTO> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<PreservationGroupPermissionDTO> returnList = new ArrayList<>();

        for(Iterator<PreservationGroup> iterPG = findPaginatedInternal(page,size).listIterator(); iterPG.hasNext(); ) {
           PreservationGroup currentPG = iterPG.next();
            for(Iterator<PreservationGroupPermission> iterBUCP = currentPG.getInheritedGroups().listIterator(); iterBUCP.hasNext();){
                PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO(iterBUCP.next());
                returnList.add(preservationGroupPermissionDTO);
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
    public List<PreservationGroupPermissionDTO> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        List<PreservationGroupPermissionDTO> returnList = new ArrayList<>();

        for(Iterator<PreservationGroup> iterPG = findAllSortedInternal(sortBy, sortOrder).listIterator(); iterPG.hasNext(); ) {
            PreservationGroup currentPG = iterPG.next();

            for(Iterator<PreservationGroupPermission> iterBUCP = currentPG.getInheritedGroups().listIterator(); iterBUCP.hasNext();){
                PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO(iterBUCP.next());
                returnList.add(preservationGroupPermissionDTO);
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
    public List<PreservationGroupPermissionDTO> findAll(final HttpServletRequest request) {
        List<PreservationGroupPermissionDTO> returnList = new ArrayList<>();
        // build return list by looping through all users
        for(Iterator<PreservationGroup> iterPG = getService().findAll().listIterator(); iterPG.hasNext(); ) {
            PreservationGroup currentPG = iterPG.next();

            for(Iterator<PreservationGroupPermission> iterBUCP = currentPG.getInheritedGroups().listIterator(); iterBUCP.hasNext();){
                PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO(iterBUCP.next());
                returnList.add(preservationGroupPermissionDTO);
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
    public List<PreservationGroupPermissionDTO> findOne(@PathVariable("id") final Long id) {
        List<PreservationGroupPermissionDTO> returnList = new ArrayList<>();
        PreservationGroup preservationGroup = getService().findOne(id);
        // build return list by looping through all users
        if( preservationGroup != null) {
            for (Iterator<PreservationGroupPermission> iterBUCP = preservationGroup.getInheritedGroups().iterator(); iterBUCP.hasNext(); ) {
                PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO(iterBUCP.next());
                returnList.add(preservationGroupPermissionDTO);
            }

        }
        else {
            throw new MyResourceNotFoundException("the path id for preservation group is not valid.");
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
    public void create(@RequestBody final PreservationGroupPermissionDTO resource) {

        PreservationGroup preservationGroup = getService().findOne(resource.getGroup_id());
        if(preservationGroup != null){
            PreservationGroupPermission preservationGroupPermission = buildPreservationGroupPermission(resource);
            preservationGroup.addGroupPermission(preservationGroupPermission);
            getService().update(preservationGroup);
        }
        else {
            throw new MyResourceNotFoundException("the payload id for preservation group is not valid.");
        }

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
    public void update(@PathVariable("id") final Long id, @RequestBody final List<PreservationGroupPermissionDTO> resource) {

        PreservationGroup preservationGroup = getService().findOne(id);
        if(preservationGroup == null){
            throw new MyBadRequestException("provided path id variable is not valid. Bad Request exception.");
        }

        // clear out current relationships
        preservationGroup.getInheritedGroups().clear();
        getService().update(preservationGroup);
        for (Iterator<PreservationGroupPermissionDTO> iterBUCPDTO = resource.iterator(); iterBUCPDTO.hasNext(); ) {
            PreservationGroupPermission preservationGroupPermission = buildPreservationGroupPermission(iterBUCPDTO.next());
            preservationGroup.addGroupPermission(preservationGroupPermission);
        }
        getService().update(preservationGroup);

        // iterate over resource and add new relationships

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id, @RequestBody final PreservationGroupPermissionDTO resource) {
        //deleteByIdInternal(id);

       PreservationGroup preservationGroup = getService().findOne(id);
        if(preservationGroup != null){
            PreservationGroupPermission preservationGroupPermission = buildPreservationGroupPermission( resource);
            preservationGroup.removeGroupPermission(preservationGroupPermission);
            getService().update(preservationGroup);
        }
        else {
            throw new MyResourceNotFoundException("the payload id for business unit is not valid.");
        }
    }



}
