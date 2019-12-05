package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyConflictException;
import gov.nara.um.persistence.dto.GroupPermissionDTO;
import gov.nara.um.persistence.dto.PreservationGroupDTO;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.util.UmMappings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
            PreservationGroup preservationGroup = iterPG.next();
            PreservationGroupDTO preservationGroupDTO = new PreservationGroupDTO();
            preservationGroupDTO.setGroup_id(preservationGroup.getId());
            preservationGroupDTO.setGroup_name(preservationGroup.getName());
            preservationGroupDTO.setGroup_description(preservationGroup.getGroup_description());
            for(Iterator<PreservationGroupPermission> iterPGP = preservationGroup.getInheritedGroups().listIterator(); iterPGP.hasNext();){
                PreservationGroupPermission preservationGroupPermission = iterPGP.next();
                GroupPermissionDTO groupPermissionDTO = new GroupPermissionDTO();
                groupPermissionDTO.setGroup_id(preservationGroupPermission.getPreservationGroupID().getId());
                groupPermissionDTO.setAssigned_group_id(preservationGroupPermission.getAssigningGroupID().getId());
                groupPermissionDTO.setPermission_level(preservationGroupPermission.getPermissionLevel());
                preservationGroupDTO.addGroupPermission(groupPermissionDTO);
            }

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

       PreservationGroup preservationGroup = findOneInternal(id);
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
            throw new MyConflictException("a preservation group with this name already exsits.");
        }

        PreservationGroup newGroup = getService().findByName(resource.getGroup_name());
        if(newGroup != null){
            for(Iterator<GroupPermissionDTO> iterPGP = resource.getGroupPermissions().listIterator(); iterPGP.hasNext();) {

                GroupPermissionDTO groupPermissionDTO = iterPGP.next();
                PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();
                preservationGroupPermission.setPreservationGroupID(newGroup);
                preservationGroupPermission.setAssigningGroupID(getService().findOne(groupPermissionDTO.getAssigned_group_id()));
                preservationGroupPermission.setPermissionLevel(groupPermissionDTO.getPermission_level());
                newGroup.addGroupPermission(preservationGroupPermission);

            }
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
            throw new MyConflictException("a preservation group with this name already exsits.");
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
    public List<PreservationGroup> findAllPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<PreservationGroup> findAllSorted(String sortBy, String sortOrder) {
        return null;
    }
}
