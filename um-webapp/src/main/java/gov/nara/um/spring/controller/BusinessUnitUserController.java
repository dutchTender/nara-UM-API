package gov.nara.um.spring.controller;



import gov.nara.common.persistence.service.ILongRawService;
import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.BusinessUnitDTO;
import gov.nara.um.persistence.dto.UserDTO;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.persistence.model.BusinessUnitConfigurationPreference;
import gov.nara.um.persistence.model.User;
import gov.nara.um.service.IBusinessUnitService;
import gov.nara.um.service.IUserService;
import gov.nara.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping(value = UmMappings.BUSINESSUNITS_USERS)
public class BusinessUnitUserController extends AbstractLongIdController<User>  {

    @Autowired
    private IUserService userService;

    @Autowired
    private IBusinessUnitService businessUnitService;


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

        List<User>  userList = userService.findAll();
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
        BusinessUnit businessUnit = businessUnitService.findOne(id.intValue());
        List<User>  userList = userService.findAll();
        // build return list by looping through all users
        if(businessUnit != null) {
            for (Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
               returnList.add(buildUserDTO( iterUser.next()));
            }
        }
        else {
            throw new MyResourceNotFoundException("the resource requested does not exist.");
        }


        return returnList;

    }







    @Override
    protected ILongRawService<User> getService() {

        return userService;
    }


    private UserDTO buildUserDTO(User user){
         UserDTO userDTO = new UserDTO();
         userDTO.setId(user.getId());
         userDTO.setName(user.getName());
         userDTO.setUser_type(user.getUser_type());
        for(Iterator<BusinessUnit> iterBU = user.getBusinessUnits().iterator(); iterBU.hasNext();) {
            userDTO.addBusinssUnitDTO(buildBusinessUnitDTO(iterBU.next()));
        }

        return userDTO;
    }

    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setId(currentBU.getId());
        businessUnitDTO.setName(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getOrg_code());
        businessUnitDTO.setLdapName(currentBU.getLdapName());

        return businessUnitDTO;
    }
}
