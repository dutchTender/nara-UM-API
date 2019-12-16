package gov.nara.um.spring.web.user;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.util.UmMappings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



@Controller
@RequestMapping(value = UmMappings.USERS)
public class UserController extends UserBaseController implements ILongIdSortingController<User> {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated and sorted
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<User> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
        return null;
    }
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllPaginatedAndSortedUserDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                        @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<UserDTO> returnList = new ArrayList<>();
        for(Iterator<User> iterUser = findPaginatedAndSortedInternal(page, size, sortBy, sortOrder).listIterator(); iterUser.hasNext(); ) {

            returnList.add(buildUserDTO(iterUser.next()));

        }

        return returnList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // find - all/paginated
    // Unit testing  : NA
    // Integration testing : NA
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<User> findAllPaginated(int page, int size) {
        return null;
    }
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllPaginatedUserDTO(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {

        List<UserDTO> returnList = new ArrayList<>();
        for(Iterator<User> iterUser = findPaginatedInternal(page,size).listIterator(); iterUser.hasNext(); ) {
            returnList.add(buildUserDTO(iterUser.next()));
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
    public List<User> findAllSorted(String sortBy, String sortOrder) {
        return null;
    }
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllSortedUserDTO(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {

        List<UserDTO> returnList = new ArrayList<>();
        for(Iterator<User> iterUser = findAllSortedInternal(sortBy, sortOrder).listIterator(); iterUser.hasNext(); ) {
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
    @Override
    public List<User> findAll(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> findAllUserDTO(final HttpServletRequest request) {

        List<UserDTO> returnList = new ArrayList<>();
        for(Iterator<User> iterUser = findAllInternal(request).listIterator(); iterUser.hasNext(); ) {
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
    public UserDTO findOne(@PathVariable("id") final Long id) {

        User currentUser = findOneInternal(id);
        if(currentUser == null){
            throw new MyResourceNotFoundException("User Id is not valid. resource is not found.");
        }
        return  buildUserDTO(currentUser);
    }

}
