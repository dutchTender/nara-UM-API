package gov.nara.um.spring.web.user;

import gov.nara.common.persistence.service.ILongRawService;
import gov.nara.common.persistence.service.IRawService;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.GroupPermissionDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.service.bussinessunits.IBusinessUnitService;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
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
        userDTO.setName(user.getName());
        userDTO.setUser_type(user.getUser_type());
        for(Iterator<BusinessUnit> iterBU = user.getBusinessUnits().iterator(); iterBU.hasNext();) {
            userDTO.addBusinssUnitDTO(buildBusinessUnitDTO(iterBU.next()));
        }
        for(Iterator<PreservationGroup> iterPG = user.getPreservationGroups().iterator(); iterPG.hasNext();) {
            userDTO.addPreservationGroupDTO(buildPreservationGroupDTO(iterPG.next()));
        }
        return userDTO;
    }

    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setBusiness_unit_id(currentBU.getId());
        businessUnitDTO.setName(currentBU.getName());
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
            GroupPermissionDTO groupPermissionDTO = new GroupPermissionDTO();
            groupPermissionDTO.setGroup_id(preservationGroupPermission.getPreservationGroupID().getId());
            groupPermissionDTO.setAssigned_group_id(preservationGroupPermission.getAssigningGroupID().getId());
            groupPermissionDTO.setPermission_level(preservationGroupPermission.getPermissionLevel());
            preservationGroupDTO.addGroupPermission(groupPermissionDTO);
        }

        return preservationGroupDTO;
    }
}
