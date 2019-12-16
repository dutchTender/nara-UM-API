package gov.nara.um.spring.web.user;

import gov.nara.common.persistence.service.ILongRawService;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupPermissionDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.persistence.model.user.UserBusinessUnit;
import gov.nara.um.persistence.model.user.UserPreservationGroup;
import gov.nara.um.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class UserBaseController extends AbstractLongIdController<User> {


    @Autowired
    private IUserService userService;




    @Override
    protected ILongRawService<User> getService() {
        return userService;
    }



    public IUserService getUserService() {
        return userService;
    }



    public UserDTO buildUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setUser_name(user.getName());
        userDTO.setUser_type(user.getUser_type());
        for(Iterator<UserBusinessUnit> iteruserBU = user.getUserBusinessUnits().iterator(); iteruserBU.hasNext();) {
            UserBusinessUnit userBusinessUnit = iteruserBU.next();
            userDTO.addBusinssUnitDTO(buildBusinessUnitDTO(userBusinessUnit.getBusinessUnitID()));
        }
        for(Iterator<UserPreservationGroup> iterUPG = user.getUserPreservationGroups().iterator(); iterUPG.hasNext();) {
            UserPreservationGroup userPreservationGroup = iterUPG.next();
            userDTO.addPreservationGroupDTO(buildPreservationGroupDTO(userPreservationGroup.getGroupID()));
        }
        return userDTO;
    }

    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setBusiness_unit_id(currentBU.getId());
        businessUnitDTO.setBusiness_unit_name(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getOrg_code());
        businessUnitDTO.setLdap_name(currentBU.getLdapName());

        return businessUnitDTO;
    }

    public PreservationGroupDTO buildPreservationGroupDTO(PreservationGroup preservationGroup){


        PreservationGroupDTO preservationGroupDTO = new PreservationGroupDTO();
        preservationGroupDTO.setGroup_id(preservationGroup.getId());
        preservationGroupDTO.setGroup_name(preservationGroup.getName());
        preservationGroupDTO.setGroup_description(preservationGroup.getGroup_description());
        for(Iterator<PreservationGroupPermission> iterPGP = preservationGroup.getInheritedGroups().iterator(); iterPGP.hasNext();) {
            PreservationGroupPermission preservationGroupPermission = iterPGP.next();
            PreservationGroupPermissionDTO preservationGroupPermissionDTO = new PreservationGroupPermissionDTO();
            //preservationGroupPermissionDTO.setGroup_id(preservationGroupPermission.getPreservationGroupID().getId());
            preservationGroupPermissionDTO.setAssigned_group_id(preservationGroupPermission.getAssigningGroupID().getId());
            preservationGroupPermissionDTO.setPermission_level(preservationGroupPermission.getPermissionLevel());
            preservationGroupDTO.addGroupPermission(preservationGroupPermissionDTO);
        }

        return preservationGroupDTO;
    }
}
