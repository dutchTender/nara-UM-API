package gov.nara.um.spring.web.user;

import gov.nara.common.util.QueryConstants;
import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.common.web.exception.MyBadRequestException;
import gov.nara.common.web.exception.MyConflictException;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
import gov.nara.um.persistence.dto.role.RoleDTO;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.persistence.model.user.UserBusinessUnit;
import gov.nara.um.persistence.model.user.UserPreservationGroup;
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

    @CrossOrigin(origins = "http://localhost:63342")
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create - one
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final UserDTO resource) {

        // validate DTO
        // we need to do some manual checks here
        // name has to be there
        // name has to be unique

        // verify that they new name does not clash with existing business unit names
        User uniqueUser = getService().findByName(resource.getUser_name());
        if(uniqueUser != null){
            throw new MyConflictException("there is already a User with that business name. Data integrity exception.");
        }

        // assumes DTO is valid
        // build User unit object
        User newUser = new User();
        newUser.setName(resource.getUser_name());
        newUser.setUser_type(resource.getUser_type());
        createInternal(newUser);

        // create roles
        List<RoleDTO> rolesList = resource.getUser_roles();
        // process user's business units and preservation groups
        List<BusinessUnitDTO> BUList = resource.getBusiness_units();
        List<PreservationGroupDTO> PGList = resource.getPreservation_groups();
        User currentUser = getService().findByName(resource.getUser_name()); // this should

        if(rolesList.size() > 0){
            for(Iterator<RoleDTO> iterRole = rolesList.listIterator(); iterRole.hasNext();){
                currentUser.addUserRole(buildUserRole(currentUser, iterRole.next()));
            }
        }

        if(BUList.size() > 0){ // we may force this size to be 1
            for(Iterator<BusinessUnitDTO> iterBU = BUList.listIterator(); iterBU.hasNext();){
                UserBusinessUnit userBusinessUnit = new UserBusinessUnit();
                userBusinessUnit.setUserID(currentUser);
                BusinessUnit businessUnit = getBusinessUnitService().findByName(iterBU.next().getBusiness_unit_name());
                if(businessUnit == null){
                    throw new MyBadRequestException("business unit for user is not valid. user association is not created.");
                }
                userBusinessUnit.setBusinessUnitID(businessUnit);
                currentUser.addUserBusinessUnit(userBusinessUnit);
            }
        }

        if(PGList.size() > 0){ // we may force this size to be 1
            for(Iterator<PreservationGroupDTO> iterPG = PGList.listIterator(); iterPG.hasNext();){
                UserPreservationGroup userPreservationGroup = new UserPreservationGroup();
                userPreservationGroup.setUserID(currentUser);
                PreservationGroup preservationGroup = getPreservationGroupService().findByName(iterPG.next().getGroup_name());
                if(preservationGroup == null){
                    throw new MyBadRequestException("preservation group for user is not valid. user association is not created.");
                }
                userPreservationGroup.setGroupID(preservationGroup);
                currentUser.addUserPreservationGroup(userPreservationGroup);
            }
        }

        getService().update(currentUser);




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
    public void update(@PathVariable("id") final Long id, @RequestBody final UserDTO resource) {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        // validate DTO
        // we need to do some manual checks here
        // name has to be there
        // name has to be unique
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        // verify that the name field is unique
        // verify that they new name does not clash with existing business unit names
        User uniqueUser = getService().findByName(resource.getUser_name());
        if(uniqueUser.getId() != id){
            throw new MyConflictException("there is already a User with that name. Data integrity exception.");
        }


        User currentUser = getService().findOne(id);
        if(currentUser == null){
            throw new MyBadRequestException("user id variable is not valid. Bad Request exception.");
        }


        // assumes DTO is valid when execution reaches here
        // build business unit object
        currentUser.setName(resource.getUser_name());
        currentUser.setUser_type(resource.getUser_type());



        List<BusinessUnitDTO> prefListBUDTO = resource.getBusiness_units();
        List <PreservationGroupDTO> prefListPGDTO = resource.getPreservation_groups();
        List<RoleDTO> rolesList = resource.getUser_roles();

        if(rolesList.size() > 0){
            currentUser.getUserRoles().clear();
            // existing preferences empty. just need to add new preferences
            // create business preference and add it to business unit
            getService().update(currentUser);
            for(Iterator<RoleDTO> iterRole = rolesList.listIterator(); iterRole.hasNext();){
                currentUser.addUserRole(buildUserRole(currentUser, iterRole.next()));
            }
        }
        else {
            currentUser.getUserBusinessUnits().clear();
            // the reason we are doing remove all here is because child relationships are not exposed
            // we have no way of removing child relationships ...

        }
        if(prefListBUDTO.size() > 0) { // input preferences is not null

            currentUser.getUserBusinessUnits().clear();
            // existing preferences empty. just need to add new preferences
            // create business preference and add it to business unit
            getService().update(currentUser);
            for(Iterator<BusinessUnitDTO> iterBU = prefListBUDTO.listIterator(); iterBU.hasNext();){
                UserBusinessUnit userBusinessUnit = new UserBusinessUnit();
                userBusinessUnit.setUserID(currentUser);
                BusinessUnit businessUnit = getBusinessUnitService().findByName(iterBU.next().getBusiness_unit_name());
                if(businessUnit == null){
                    throw new MyBadRequestException("business unit for user is not valid. user association is not created.");
                }
                userBusinessUnit.setBusinessUnitID(businessUnit);
                currentUser.addUserBusinessUnit(userBusinessUnit);
            }

            //getService().update(currentUser);

        }
        else {
            // resource business unit lists is empty
            currentUser.getUserBusinessUnits().clear();

        }
        if(prefListPGDTO.size() > 0) { // input preferences is not null

            currentUser.getUserPreservationGroups().clear();
            // existing preferences empty. just need to add new preferences
            // create business preference and add it to business unit
            getService().update(currentUser);
            for(Iterator<PreservationGroupDTO> iterPG = prefListPGDTO.listIterator(); iterPG.hasNext();){
                UserPreservationGroup userPreservationGroup = new UserPreservationGroup();
                userPreservationGroup.setUserID(currentUser);
                PreservationGroup preservationGroup = getPreservationGroupService().findByName(iterPG.next().getGroup_name());
                if(preservationGroup == null){
                    throw new MyBadRequestException("preservation group for user is not valid. user association is not created.");
                }
                userPreservationGroup.setGroupID(preservationGroup);
                currentUser.addUserPreservationGroup(userPreservationGroup);
            }

            //getService().update(currentUser);

        }
        else {
            // resource business unit lists is empty
            currentUser.getUserPreservationGroups().clear();

        }
        getService().update(currentUser);
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

}
