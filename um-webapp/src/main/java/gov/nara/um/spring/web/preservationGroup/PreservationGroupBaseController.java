package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupPermissionDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
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

    protected List<PreservationGroupDTO> buildPrservationGroupDTOList(List<PreservationGroup> preservationGroupList){
        List<PreservationGroupDTO> returnList = new ArrayList<>();

        for(Iterator<PreservationGroup> iterPG = preservationGroupList.listIterator(); iterPG.hasNext(); ) {
            PreservationGroupDTO preservationGroupDTO = buildPreservationGroupDTO(iterPG.next());
            returnList.add(preservationGroupDTO);

        }

        return returnList;

    }



    protected PreservationGroupDTO buildPreservationGroupDTO(PreservationGroup currentPG){

        PreservationGroupDTO preservationGroupDTO = new PreservationGroupDTO();
        preservationGroupDTO.setGroup_id(currentPG.getId());
        preservationGroupDTO.setGroup_name(currentPG.getName());
        preservationGroupDTO.setGroup_description(currentPG.getGroup_description());

        for(Iterator<PreservationGroupPermission> iterPGP = currentPG.getInheritedGroups().listIterator(); iterPGP.hasNext();){
            PreservationGroupPermissionDTO preservationGroupPermissionDTO = buildPreservationGroupPermissionDTO( iterPGP.next());
            preservationGroupDTO.addGroupPermission(preservationGroupPermissionDTO);
        }
        return preservationGroupDTO;

    }


    protected PreservationGroup buildPreservationGroup(PreservationGroupDTO currentPGDTO){

        PreservationGroup preservationGroup = new PreservationGroup();
        preservationGroup.setId(currentPGDTO.getGroup_id());
        preservationGroup.setName(currentPGDTO.getGroup_name());
        preservationGroup.setGroup_description(currentPGDTO.getGroup_description());

        for(Iterator<PreservationGroupPermissionDTO> iterPGPDTO = currentPGDTO.getGroup_permissions().listIterator(); iterPGPDTO.hasNext();){
           PreservationGroupPermission preservationGroupPermission = buildPreservationGroupPermission( iterPGPDTO.next());
           preservationGroup.addGroupPermission(preservationGroupPermission);
        }
        return preservationGroup;

    }


    protected PreservationGroupPermissionDTO buildPreservationGroupPermissionDTO(PreservationGroupPermission currentPGP){
        PreservationGroupPermissionDTO preservationGroupPermissionDTO = new PreservationGroupPermissionDTO();
        preservationGroupPermissionDTO.setGroup_id(currentPGP.getPreservationGroupID().getId());
        preservationGroupPermissionDTO.setAssigned_group_id(currentPGP.getAssigningGroupID().getId());
        preservationGroupPermissionDTO.setPermission_level(currentPGP.getPermissionLevel());

        return preservationGroupPermissionDTO;
    }


   protected PreservationGroupPermission buildPreservationGroupPermission(PreservationGroupPermissionDTO currentPGPDTO){
       PreservationGroupPermission preservationGroupPermission = new PreservationGroupPermission();
       preservationGroupPermission.setAssigningGroupID(getService().findOne(currentPGPDTO.getAssigned_group_id()));
       preservationGroupPermission.setPreservationGroupID(getService().findOne(currentPGPDTO.getGroup_id()));
       preservationGroupPermission.setPermissionLevel(currentPGPDTO.getPermission_level());

        return preservationGroupPermission;
    }

    protected PreservationGroup findOneGroupbyID(Long id){

        List<PreservationGroup> resultList = getService().findAll();
        PreservationGroup returnGroup = null;
        for(Iterator<PreservationGroup> iterPG = resultList.listIterator(); iterPG.hasNext();){

            PreservationGroup currentPG = iterPG.next();
            if(id == currentPG.getId()){
                returnGroup = currentPG;
                break;
            }
        }

        return returnGroup;

    }
}
