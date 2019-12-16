package gov.nara.um.persistence.dto.preservationgroups;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "group_id", "group_name", "group_description", "group_permissions" })
public class PreservationGroupDTO {

    private Long group_id;
    private String group_name;
    private String group_description;

    private List<PreservationGroupPermissionDTO> group_permissions = new ArrayList<>();


    public void addGroupPermission(PreservationGroupPermissionDTO preservationGroupPermissionDTO){
        group_permissions.add(preservationGroupPermissionDTO);
    }
    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public List<PreservationGroupPermissionDTO> getGroup_permissions() {
        return group_permissions;
    }

    public void setGroup_permissions(List<PreservationGroupPermissionDTO> group_permissions) {
        this.group_permissions = group_permissions;
    }
}
