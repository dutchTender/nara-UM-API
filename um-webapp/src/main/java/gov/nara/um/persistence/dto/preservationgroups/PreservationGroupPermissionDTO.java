package gov.nara.um.persistence.dto.preservationgroups;

public class PreservationGroupPermissionDTO {

    private Long assigned_group_id;
    private String permission_level;



    public Long getAssigned_group_id() {
        return assigned_group_id;
    }

    public void setAssigned_group_id(Long assigned_group_id) {
        this.assigned_group_id = assigned_group_id;
    }

    public String getPermission_level() {
        return permission_level;
    }

    public void setPermission_level(String permission_level) {
        this.permission_level = permission_level;
    }
}
