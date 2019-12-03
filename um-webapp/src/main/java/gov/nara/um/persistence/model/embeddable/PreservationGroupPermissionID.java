package gov.nara.um.persistence.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PreservationGroupPermissionID implements Serializable {

    @Column( name = "group_id")
    private Long groupId;
    @Column( name = "assigned_group_id")
    private Long assignedGroupId;


    public PreservationGroupPermissionID(Long groupId, Long assignedGroupId) {
        this.groupId = groupId;
        this.assignedGroupId = assignedGroupId;
    }

    public PreservationGroupPermissionID() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAssignedGroupId() {
        return assignedGroupId;
    }

    public void setAssignedGroupId(Long assignedGroupId) {
        this.assignedGroupId = assignedGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreservationGroupPermissionID)) return false;
        PreservationGroupPermissionID that = (PreservationGroupPermissionID) o;
        return getGroupId().equals(that.getGroupId()) &&
                getAssignedGroupId().equals(that.getAssignedGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getAssignedGroupId());
    }


}
