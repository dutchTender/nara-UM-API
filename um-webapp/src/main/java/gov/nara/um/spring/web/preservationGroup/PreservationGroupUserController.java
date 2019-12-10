package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import gov.nara.um.spring.web.user.UserBaseController;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping(value = UmMappings.PERSERVATIONGROUPS_USERS)
public class PreservationGroupUserController extends UserBaseController {


    @Autowired
    private IPreservationGroupService preservationGroupServices;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<UserDTO> returnList = new ArrayList<>();
        List<User>  userList =  findPaginatedInternal(page,size);
        // build return list by looping through all users
        for(Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
            returnList.add(buildUserDTO(iterUser.next()));
        }
        return returnList;
    }



    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<UserDTO> returnList = new ArrayList<>();
        List<User>  userList =  findAllSortedInternal(sortBy, sortOrder);
        // build return list by looping through all users
        for(Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
            returnList.add(buildUserDTO(iterUser.next()));
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
    public List<UserDTO> findAll(final HttpServletRequest request) {

        // return all users that belongs to any business unit
        List<UserDTO> returnList = new ArrayList<>();
        List<User>  userList = getService().findAll();
        // build return list by looping through all users
        for(Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
            returnList.add(buildUserDTO(iterUser.next()));
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
    public List<UserDTO> findOne(@PathVariable("id") final Long id) {
        // return all users that belongs to a business unit
        List<UserDTO> returnList = new ArrayList<>();
        PreservationGroup  preservationGroup = getPreservationGroupServices().findOne(id);
        List<User>  userList = getService().findAll();
        // build return list by looping through all users
        if(preservationGroup != null) {
            for (Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
                User user = iterUser.next();
                if(user.getPreservationGroups().contains(preservationGroup)) {
                    returnList.add(buildUserDTO(user));
                }
            }
        }
        else {
            throw new MyResourceNotFoundException("the resource requested does not exist.");
        }


        return returnList;

    }


    public IPreservationGroupService getPreservationGroupServices() {
        return preservationGroupServices;
    }

    public void setPreservationGroupServices(IPreservationGroupService preservationGroupServices) {
        this.preservationGroupServices = preservationGroupServices;
    }
}
