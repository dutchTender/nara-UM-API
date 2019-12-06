package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyConflictException;
import gov.nara.um.persistence.dto.GroupPermissionDTO;
import gov.nara.um.persistence.dto.PreservationGroupDTO;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.util.UmMappings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = UmMappings.PERSERVATIONGROUPS)
public class PreservationGroupController extends  PreservationGroupBaseController  implements ILongIdSortingController<PreservationGroup> {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @ResponseBody
    public List<PreservationGroup> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                           @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        //return buildPrservationGroupDTOList(findPaginatedAndSortedInternal(page, size, sortBy, sortOrder));
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<PreservationGroupDTO> findAllPaginatedAndSortedPG(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                             @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return buildPrservationGroupDTOList(findPaginatedAndSortedInternal(page, size, sortBy, sortOrder));

    }



    @Override
    public List<PreservationGroup> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return findPaginatedInternal(page, size);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<PreservationGroupDTO> findAllPaginatedPG(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<PreservationGroupDTO> returnList = new ArrayList<>();

        for(Iterator<PreservationGroup> iterPG = findPaginatedInternal(page,size).listIterator(); iterPG.hasNext(); ) {
           PreservationGroupDTO preservationGroupDTO = buildPreservationGroupDTO(iterPG.next());
           returnList.add(preservationGroupDTO);
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
    public List<PreservationGroupDTO> findAllPG(HttpServletRequest request) {

        List<PreservationGroup> resultList =  findAllInternal(request);
        List<PreservationGroupDTO> returnList = new ArrayList<>();
        for(Iterator<PreservationGroup> iterPG = resultList.listIterator(); iterPG.hasNext();){
            PreservationGroupDTO preservationGroupDTO = buildPreservationGroupDTO(iterPG.next());
            returnList.add(preservationGroupDTO);
        }

        return  returnList;
    }

    @Override
    public List<PreservationGroup> findAll(HttpServletRequest request) {
        return findAllInternal(request);
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
    public PreservationGroupDTO findOne(@PathVariable("id") final Long id) {

       PreservationGroup preservationGroup = findOneGroupbyID(id);
       PreservationGroupDTO preservationGroupDTO = buildPreservationGroupDTO(preservationGroup);
       return preservationGroupDTO;
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
    public void create(@RequestBody final PreservationGroupDTO resource) {

        PreservationGroup preservationGroup = new PreservationGroup();
        preservationGroup.setName(resource.getGroup_name());
        preservationGroup.setGroup_description(resource.getGroup_description());

        if(getService().findByName(resource.getGroup_name()) == null){
            createInternal(preservationGroup);
        }
        else {
            throw new MyConflictException("a preservation group with this name already exists.");
        }

        PreservationGroup newGroup = getService().findByName(resource.getGroup_name());
        if(newGroup != null){
            for(Iterator<GroupPermissionDTO> iterPGP = resource.getGroup_permissions().listIterator(); iterPGP.hasNext();) {

                GroupPermissionDTO groupPermissionDTO = iterPGP.next();
                PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();
                preservationGroupPermission.setPreservationGroupID(newGroup);
                preservationGroupPermission.setAssigningGroupID(findOneGroupbyID(groupPermissionDTO.getAssigned_group_id()));
                preservationGroupPermission.setPermissionLevel(groupPermissionDTO.getPermission_level());
                newGroup.addGroupPermission(preservationGroupPermission);

            }
        }
        else {
            throw new MyBadRequestException("Resource could not be created. Database not available");
        }

        getService().update(newGroup);
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody final PreservationGroupDTO resource) {

     PreservationGroup  updateGroup = buildPreservationGroup(resource);

        if(getService().findByName(resource.getGroup_name()) == null){
            getService().update(updateGroup);
        }
        else {
            throw new MyConflictException("a preservation group with this name already exists.");
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
    public void delete(@PathVariable("id") final Long id) {


        deleteByIdInternal(id);
    }


    @Override
    public List<PreservationGroup> findAllSorted(String sortBy, String sortOrder) {
        return null;
    }
}
