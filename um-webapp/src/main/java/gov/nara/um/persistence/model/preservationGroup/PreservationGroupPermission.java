package gov.nara.um.persistence.model.preservationGroup;


import gov.nara.um.persistence.model.embeddable.PreservationGroupPermissionID;

import javax.persistence.*;
import java.util.Objects;

@Entity
//@Table(name = "group_permissions", schema = "oif_ods")
@Table(name = "group_permissions")
public class PreservationGroupPermission {

    @EmbeddedId
    private PreservationGroupPermissionID id = new PreservationGroupPermissionID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("preservationGroupID")
    @JoinColumn(name="group_id", nullable=false)
    private PreservationGroup preservationGroupID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("assigningGroupID")
    @JoinColumn(name="assigned_group_id", nullable=false)
    private PreservationGroup assigningGroupID;


    @Column(name = "permission_level")
    private String permissionLevel;

    public PreservationGroupPermission(PreservationGroup preservationGroupID, PreservationGroup assigningGroupID) {
        this.preservationGroupID = preservationGroupID;
        this.assigningGroupID = assigningGroupID;
    }

    public PreservationGroupPermission() {
    }

    public PreservationGroupPermissionID getId() {
        return id;
    }

    public void setId(PreservationGroupPermissionID id) {
        this.id = id;
    }

    public PreservationGroup getPreservationGroupID() {
        return preservationGroupID;
    }

    public void setPreservationGroupID(PreservationGroup preservationGroupID) {
        this.preservationGroupID = preservationGroupID;
    }

    public PreservationGroup getAssigningGroupID() {
        return assigningGroupID;
    }

    public void setAssigningGroupID(PreservationGroup assigningGroupID) {
        this.assigningGroupID = assigningGroupID;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreservationGroupPermission)) return false;
        PreservationGroupPermission that = (PreservationGroupPermission) o;
        return getId().equals(that.getId()) &&
                getPreservationGroupID().equals(that.getPreservationGroupID()) &&
                getAssigningGroupID().equals(that.getAssigningGroupID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPreservationGroupID(), getAssigningGroupID());
    }
}
