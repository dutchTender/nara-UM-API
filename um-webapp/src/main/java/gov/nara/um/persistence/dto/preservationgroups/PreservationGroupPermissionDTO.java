package gov.nara.um.persistence.dto.preservationgroups;

public class PreservationGroupPermissionDTO {

    private Long group_id;
    private Long assigned_group_id;
    private String permission_level;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

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
