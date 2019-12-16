package gov.nara.um.persistence.model.user;


import gov.nara.um.persistence.model.embeddable.UserRoleID;
import gov.nara.um.persistence.model.role.Role;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_role", schema = "oif_ods")
//@Table(name = "user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleID id = new UserRoleID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_id")
    @JoinColumn(name="user_id", nullable=false)
    private User userID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("role_id")
    @JoinColumn(name="role_id", nullable=false)
    private Role roleID;


    public UserRole(User userID, Role roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public UserRole() {
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return getUserID().equals(userRole.getUserID()) &&
                getRoleID().equals(userRole.getRoleID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getRoleID());
    }
}
