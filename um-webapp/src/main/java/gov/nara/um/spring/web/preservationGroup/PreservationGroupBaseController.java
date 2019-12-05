package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dto.GroupPermissionDTO;
import gov.nara.um.persistence.dto.PreservationGroupDTO;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroupPermission;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreservationGroupBaseController extends AbstractLongIdController<PreservationGroup> {


    @Autowired
    private IPreservationGroupService preservationGroupService;



    @Override
    protected IPreservationGroupService getService() {
        return preservationGroupService;
    }

    public List<PreservationGroupDTO> buildPrservationGroupDTOList(List<PreservationGroup> preservationGroupList){
        List<PreservationGroupDTO> returnList = new ArrayList<>();

        for(Iterator<PreservationGroup> iterPG = preservationGroupList.listIterator(); iterPG.hasNext(); ) {
            PreservationGroupDTO preservationGroupDTO = buildPreservationGroupDTO(iterPG.next());
            returnList.add(preservationGroupDTO);

        }

        return returnList;

    }



    public PreservationGroupDTO buildPreservationGroupDTO(PreservationGroup currentPG){

        PreservationGroupDTO preservationGroupDTO = new PreservationGroupDTO();
        preservationGroupDTO.setGroup_id(currentPG.getId());
        preservationGroupDTO.setGroup_name(currentPG.getName());
        preservationGroupDTO.setGroup_description(currentPG.getGroup_description());

        for(Iterator<PreservationGroupPermission> iterPGP = currentPG.getInheritedGroups().listIterator(); iterPGP.hasNext();){
            GroupPermissionDTO groupPermissionDTO = buildPreservationGroupPermissionDTO( iterPGP.next());
            preservationGroupDTO.addGroupPermission(groupPermissionDTO);
        }
        return preservationGroupDTO;

    }


    public PreservationGroup buildPreservationGroup(PreservationGroupDTO currentPGDTO){

        PreservationGroup preservationGroup = new PreservationGroup();
        preservationGroup.setId(currentPGDTO.getGroup_id());
        preservationGroup.setName(currentPGDTO.getGroup_name());
        preservationGroup.setGroup_description(currentPGDTO.getGroup_description());

        for(Iterator<GroupPermissionDTO> iterPGPDTO = currentPGDTO.getGroupPermissions().listIterator(); iterPGPDTO.hasNext();){
           PreservationGroupPermission preservationGroupPermission = buildPreservationGroupPermission( iterPGPDTO.next());
           preservationGroup.addGroupPermission(preservationGroupPermission);
        }
        return preservationGroup;

    }


    public GroupPermissionDTO buildPreservationGroupPermissionDTO(PreservationGroupPermission currentPGP){
        GroupPermissionDTO groupPermissionDTO = new GroupPermissionDTO();
        groupPermissionDTO.setGroup_id(currentPGP.getPreservationGroupID().getId());
        groupPermissionDTO.setAssigned_group_id(currentPGP.getAssigningGroupID().getId());
        groupPermissionDTO.setPermission_level(currentPGP.getPermissionLevel());

        return groupPermissionDTO;
    }


    public PreservationGroupPermission buildPreservationGroupPermission(GroupPermissionDTO currentPGPDTO){
       PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();
       preservationGroupPermission.setAssigningGroupID(getService().findOne(currentPGPDTO.getAssigned_group_id()));
       preservationGroupPermission.setPreservationGroupID(getService().findOne(currentPGPDTO.getGroup_id()));
       preservationGroupPermission.setPermissionLevel(currentPGPDTO.getPermission_level());

        return preservationGroupPermission;
    }
}
