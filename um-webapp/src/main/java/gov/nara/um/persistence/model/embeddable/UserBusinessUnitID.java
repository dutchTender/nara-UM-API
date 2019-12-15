package gov.nara.um.persistence.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserBusinessUnitID implements Serializable {

    @Column( name = "user_id")
    private Long user_id;
    @Column( name = "business_unit_id")
    private Integer business_unit_id;

    public UserBusinessUnitID(Long user_id, Integer business_unit_id) {
        this.user_id = user_id;
        this.business_unit_id = business_unit_id;
    }

    public UserBusinessUnitID() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getBusiness_unit_id() {
        return business_unit_id;
    }

    public void setBusiness_unit_id(Integer business_unit_id) {
        this.business_unit_id = business_unit_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBusinessUnitID)) return false;
        UserBusinessUnitID that = (UserBusinessUnitID) o;
        return getUser_id().equals(that.getUser_id()) &&
                getBusiness_unit_id().equals(that.getBusiness_unit_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getBusiness_unit_id());
    }
}
