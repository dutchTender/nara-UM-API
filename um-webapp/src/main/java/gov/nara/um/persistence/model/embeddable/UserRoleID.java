package gov.nara.um.persistence.model.embeddable;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class UserRoleID implements Serializable {


    @Column( name = "user_id")
    private Long user_id;
    @Column( name = "role_id")
    private Long role_id;

    public UserRoleID(Long user_id, Long role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public UserRoleID() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleID)) return false;
        UserRoleID that = (UserRoleID) o;
        return getUser_id().equals(that.getUser_id()) &&
                getRole_id().equals(that.getRole_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getRole_id());
    }
}
