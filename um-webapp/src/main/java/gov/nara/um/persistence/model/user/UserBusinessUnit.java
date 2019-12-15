package gov.nara.um.persistence.model.user;


import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.embeddable.UserBusinessUnitID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_business_unit", schema = "oif_ods")
//@Table(name = "user_business_unit")
public class UserBusinessUnit {

    @EmbeddedId
    private UserBusinessUnitID id = new UserBusinessUnitID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_id")
    @JoinColumn(name="user_id", nullable=false)
    private User userID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("business_unit_id")
    @JoinColumn(name="business_unit_id", nullable=false)
    private BusinessUnit businessUnitID;

    public UserBusinessUnit(User userID, BusinessUnit businessUnitID) {
        this.userID = userID;
        this.businessUnitID = businessUnitID;
    }

    public UserBusinessUnit() {
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public BusinessUnit getBusinessUnitID() {
        return businessUnitID;
    }

    public void setBusinessUnitID(BusinessUnit businessUnitID) {
        this.businessUnitID = businessUnitID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBusinessUnit)) return false;
        UserBusinessUnit that = (UserBusinessUnit) o;
        return getUserID().equals(that.getUserID()) &&
                getBusinessUnitID().equals(that.getBusinessUnitID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getBusinessUnitID());
    }
}
