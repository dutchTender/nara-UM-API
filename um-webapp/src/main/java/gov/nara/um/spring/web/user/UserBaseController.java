package gov.nara.um.spring.web.user;

import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupPermissionDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
import gov.nara.um.persistence.dto.role.RoleDTO;
import gov.nara.um.persistence.dto.user.UserDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.persistence.model.user.User;
import gov.nara.um.persistence.model.user.UserBusinessUnit;
import gov.nara.um.persistence.model.user.UserPreservationGroup;
import gov.nara.um.persistence.model.user.UserRole;
import gov.nara.um.service.bussinessunits.IBusinessUnitService;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import gov.nara.um.service.role.IRoleService;
import gov.nara.um.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.Iterator;

public class UserBaseController extends AbstractLongIdController<User> {


    @Autowired
    private IUserService userService;

    @Override
    protected IUserService getService() {
        return userService;
    }

    @Autowired
    private IBusinessUnitService businessUnitService;

    @Autowired
    private IPreservationGroupService preservationGroupService;

    @Autowired
    private IRoleService roleService;

    public IBusinessUnitService getBusinessUnitService() {
        return businessUnitService;
    }


    public IPreservationGroupService getPreservationGroupService() {
        return preservationGroupService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public UserDTO buildUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setUser_name(user.getName());
        userDTO.setUser_type(user.getUser_type());

        for(Iterator<UserRole> iterRole = user.getUserRoles().iterator(); iterRole.hasNext();) {
            userDTO.addRoleDTO(buildRoleDTO(iterRole.next()));
        }

        for(Iterator<UserBusinessUnit> iteruserBU = user.getUserBusinessUnits().iterator(); iteruserBU.hasNext();) {
            UserBusinessUnit userBusinessUnit = iteruserBU.next();
            userDTO.addBusinssUnitDTO(buildBusinessUnitDTO(userBusinessUnit.getBusinessUnitID()));
        }
        if(userDTO.getBusiness_units().size() == 0){
            // add default Not Available business unit
            BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
            businessUnitDTO.setBusiness_unit_name("Not Available");
            userDTO.addBusinssUnitDTO(businessUnitDTO);
        }
        for(Iterator<UserPreservationGroup> iterUPG = user.getUserPreservationGroups().iterator(); iterUPG.hasNext();) {
            UserPreservationGroup userPreservationGroup = iterUPG.next();
            userDTO.addPreservationGroupDTO(buildPreservationGroupDTO(userPreservationGroup.getGroupID()));
        }
        if(userDTO.getPreservation_groups().size() == 0){
            // add default Not Available preservation group
            PreservationGroupDTO preservationGroupDTO = new PreservationGroupDTO();
            preservationGroupDTO.setGroup_name("Not Available");
            userDTO.addPreservationGroupDTO(preservationGroupDTO);
        }

        return userDTO;
    }

    public RoleDTO buildRoleDTO(UserRole userRole){

           RoleDTO roleDTO = new RoleDTO();
           roleDTO.setRole_id(userRole.getRoleID().getId());
           roleDTO.setRole_description(userRole.getRoleID().getRole_description());
           roleDTO.setRole_name(userRole.getRoleID().getName());
           return  roleDTO;
    }
    public UserRole buildUserRole(User user, RoleDTO roleDTO){
        UserRole userRole = new UserRole();
        userRole.setRoleID(getRoleService().findOne(roleDTO.getRole_id()));
        userRole.setUserID(user);
        return userRole;
    }



    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setBusiness_unit_id(currentBU.getId());
        businessUnitDTO.setBusiness_unit_name(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getCode());
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


    public void createLDAPEntry(User user){
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldaps://vpnsrv01.oifd.archives.gov:10636");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL,"uid=admin,ou=system"); // specify the username
        env.put(Context.SECURITY_CREDENTIALS,"naraoif1234!");// specify the password
        // TODO code application logic here

        // entry's DN
        String entryDN = "uid=user1,ou=system";

        // entry's attributes
        // The new external user must be created in the ERA 2.0 Apache Directory by using ApacheDS studio.
        // The required attributes are email address, uid, sn, and password.


        Attribute cn = new BasicAttribute("cn", "li zhang");
        Attribute sn = new BasicAttribute("sn", "zhang");
        Attribute mail = new BasicAttribute("mail", "li.zhang@nara.gov");
        //Attribute phone = new BasicAttribute("telephoneNumber", "+1 222 3334444");
        Attribute oc = new BasicAttribute("objectClass");
        oc.add("Dev/Test");
        oc.add("oifd");
        oc.add("archives");
        oc.add("gov");

        DirContext ctx = null;

        try {
            // get a handle to an Initial DirContext
            ctx = new InitialDirContext(env);

            // build the entry
            BasicAttributes entry = new BasicAttributes();
            entry.put(cn);
            entry.put(sn);
            entry.put(mail);
            //entry.put(phone);

            entry.put(oc);

            // Add the entry

            ctx.createSubcontext(entryDN, entry);
            //System.out.println( "AddUser: added entry " + entryDN + ".");

        } catch (NamingException e) {
            System.err.println("AddUser: error adding entry." + e);
        }

    }
}
