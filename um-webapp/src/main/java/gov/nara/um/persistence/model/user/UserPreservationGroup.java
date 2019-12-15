package gov.nara.um.persistence.model.user;


import gov.nara.um.persistence.model.embeddable.UserPreservationGroupID;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_group", schema = "oif_ods")
//@Table(name = "user_group")
public class UserPreservationGroup {
    @EmbeddedId
    private UserPreservationGroupID id = new UserPreservationGroupID();


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_id")
    @JoinColumn(name="user_id", nullable=false)
    private User userID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("group_id")
    @JoinColumn(name="group_id", nullable=false)
    private PreservationGroup groupID;

    public UserPreservationGroup(User userID, PreservationGroup groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }

    public UserPreservationGroup() {
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public PreservationGroup getGroupID() {
        return groupID;
    }

    public void setGroupID(PreservationGroup groupID) {
        this.groupID = groupID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPreservationGroup)) return false;
        UserPreservationGroup that = (UserPreservationGroup) o;
        return getUserID().equals(that.getUserID()) &&
                getGroupID().equals(that.getGroupID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getGroupID());
    }
}
