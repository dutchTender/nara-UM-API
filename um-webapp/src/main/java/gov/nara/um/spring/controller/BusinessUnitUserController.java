package gov.nara.um.spring.controller;



import gov.nara.common.persistence.service.ILongRawService;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.common.web.exception.MyResourceNotFoundException;
import gov.nara.um.persistence.model.BusinessUnit;
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
    // find - all
    // Unit testing  : NA
    // Integration testing : NA
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAll(final HttpServletRequest request) {

        // return all users that belongs to any business unit
        List<User> returnList = new ArrayList<User>();
        List<User>  userList = userService.findAll();
        // build return list by looping through all users
        for(Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
            User current = iterUser.next();
           if(current.getBusinessUnits().size() > 0){
               returnList.add(current);
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
    public List<User> findOne(@PathVariable("id") final Long id) {
        // return all users that belongs to a business unit
        List<User> returnList = new ArrayList<User>();
        BusinessUnit businessUnit = businessUnitService.findOne(id.intValue());
        List<User>  userList = userService.findAll();
        // build return list by looping through all users
        if(businessUnit != null) {

            for (Iterator<User> iterUser = userList.iterator(); iterUser.hasNext(); ) {
                User current = iterUser.next();
                for (Iterator<BusinessUnit> iterBU = current.getBusinessUnits().iterator(); iterBU.hasNext(); ) {
                    BusinessUnit currentBU = iterBU.next();
                    if(currentBU.getName().equals(businessUnit.getName())){
                        returnList.add(current);
                    }
                }

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


}
