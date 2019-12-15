package gov.nara.um.persistence.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserPreservationGroupID implements Serializable {

    @Column( name = "user_id")
    private Long user_id;
    @Column( name = "group_id")
    private Long group_id;


    public UserPreservationGroupID(Long user_id, Long group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public UserPreservationGroupID() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPreservationGroupID)) return false;
        UserPreservationGroupID that = (UserPreservationGroupID) o;
        return getUser_id().equals(that.getUser_id()) &&
                getGroup_id().equals(that.getGroup_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getGroup_id());
    }
}
