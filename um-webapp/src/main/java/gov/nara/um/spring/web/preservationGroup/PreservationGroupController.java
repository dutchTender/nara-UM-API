package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyBadRequestException;
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
        if(newGroupAdded == null){
            throw new MyBadRequestException("new group added returned null");

        }
        newGroupAdded.addGroupPermission(preservationGroupPermission);




        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");


        //newGroupAdded.addGroupPermission(preservationGroupPermission);



        getService().update(newGroupAdded);

        //System.out.println(returnPerm.toString());
        referenceGroup.addAssignGroupPermission(preservationGroupPermission);



        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        getService().update(referenceGroup);

        //getService().update(referenceGroup);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

       //defaultGroup.addAssigningGroupPermission(preservationGroupPermission);
        //getService().update(defaultGroup);

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

    }
}
