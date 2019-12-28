package gov.nara.um.persistence.model.embeddable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BusinessUnitConfigurationID implements Serializable {

    @Column( name = "business_unit_id")
    private Integer businessUnitID;
    @Column( name = "business_unit_config_id")
    private Integer businessUnitConfigID;

    public BusinessUnitConfigurationID(Integer businessUnitID, Integer businessUnitConfigID) {
        this.businessUnitID = businessUnitID;
        this.businessUnitConfigID = businessUnitConfigID;
    }

    public BusinessUnitConfigurationID() {
    }


    public Integer getBusinessUnitID() {
        return businessUnitID;
    }

    public void setBusinessUnitID(Integer businessUnitID) {
        this.businessUnitID = businessUnitID;
    }

    public Integer getBusinessUnitConfigID() {
        return businessUnitConfigID;
    }

    public void setBusinessUnitConfigID(Integer businessUnitConfigID) {
        this.businessUnitConfigID = businessUnitConfigID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessUnitConfigurationID)) return false;
        BusinessUnitConfigurationID that = (BusinessUnitConfigurationID) o;
        return getBusinessUnitID().equals(that.getBusinessUnitID()) &&
                getBusinessUnitConfigID().equals(that.getBusinessUnitConfigID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessUnitID(), getBusinessUnitConfigID());
    }
}
