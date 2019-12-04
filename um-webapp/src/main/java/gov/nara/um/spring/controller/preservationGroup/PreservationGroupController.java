package gov.nara.um.spring.controller.preservationGroup;

import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.um.persistence.model.preservationGroup.AssigningGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.util.UmMappings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        createInternal(preservationGroup);


        AssigningGroup defaultGroup = new AssigningGroup();
        defaultGroup.setName("default");
        defaultGroup.setGroup_description(resource.getGroup_description());

        if(getAssigningGroupService().findByName(defaultGroup.getName()) == null){
            getAssigningGroupService().create(defaultGroup);
        }



        PreservationGroup newGroupAdded = getService().findByName(resource.getName());
        AssigningGroup referenceGroup = getAssigningGroupService().findByName(defaultGroup.getName());

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("added group id :"+newGroupAdded.getId());
        System.out.println("reference group id :"+referenceGroup.getId());

        if(newGroupAdded == null){
            throw new MyBadRequestException("new group added returned null");

        }

        PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();
        preservationGroupPermission.setPreservationGroupID(newGroupAdded);
        preservationGroupPermission.setAssigningGroupID(referenceGroup);
        preservationGroupPermission.setPermissionLevel("Content");


        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");


        newGroupAdded.addGroupPermission(preservationGroupPermission);



        getService().update(newGroupAdded);
        //referenceGroup.addAssigningGroupPermission(preservationGroupPermission);
        //System.out.println(returnPerm.toString());

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");



        //getService().update(referenceGroup);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

       //defaultGroup.addAssigningGroupPermission(preservationGroupPermission);
        //getService().update(defaultGroup);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

    }
}
