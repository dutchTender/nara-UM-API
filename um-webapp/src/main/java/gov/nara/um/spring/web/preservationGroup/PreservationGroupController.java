package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyBadRequestException;
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


    @Override
    public List<PreservationGroup> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
        return null;
    }
    @Override
    public List<PreservationGroup> findAllPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<PreservationGroup> findAllSorted(String sortBy, String sortOrder) {
        return null;
    }


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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final PreservationGroup resource) {

        PreservationGroup preservationGroup = new PreservationGroup();
        preservationGroup.setName(resource.getName());
        preservationGroup.setGroup_description(resource.getGroup_description());

        if(getService().findByName(resource.getName()) == null){
            createInternal(preservationGroup);
        }



        PreservationGroup defaultGroup = new PreservationGroup();
        defaultGroup.setName("default");
        //defaultGroup.setGroup_permission("Content");
        defaultGroup.setGroup_description(resource.getGroup_description());
        if(getService().findByName(defaultGroup.getName()) == null){
            createInternal(defaultGroup);
        }



        PreservationGroup newGroupAdded = getService().findByName(resource.getName());
        PreservationGroup referenceGroup = getService().findByName(defaultGroup.getName());

        PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("added group id :"+newGroupAdded.getId());
        System.out.println("reference group id :"+referenceGroup.getId());

        preservationGroupPermission.setAssigningGroupID(referenceGroup);
        preservationGroupPermission.setPreservationGroupID(newGroupAdded);
        preservationGroupPermission.setPermissionLevel("your mom");

        newGroupAdded.addGroupPermission(preservationGroupPermission);




        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        getService().update(newGroupAdded);


        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        //getService().update(referenceGroup);

        //getService().update(referenceGroup);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

       //defaultGroup.addAssigningGroupPermission(preservationGroupPermission);
        //getService().update(defaultGroup);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

    }




}
