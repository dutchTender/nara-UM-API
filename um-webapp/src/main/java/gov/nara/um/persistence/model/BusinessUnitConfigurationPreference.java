package gov.nara.um.persistence.model;



import javax.persistence.*;
import java.util.Objects;

@Entity
//@Table(name = "business_unit_config_values", schema = "oif_ods")
@Table(name = "business_unit_config_values")
public class BusinessUnitConfigurationPreference {

    @EmbeddedId
    private BusinessUnitConfigurationID id = new BusinessUnitConfigurationID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("businessUnitID")
    @JoinColumn(name="business_unit_catalog_id", nullable=false)
    private BusinessUnit businessUnitID;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("businessUnitConfigID")
    @JoinColumn(name="business_unit_config_id", nullable=false)
    private BusinessUnitConfiguration businessUnitConfigID;


    @Column(name = "configuration_value")
    private String configurationValue;

    public BusinessUnitConfigurationPreference() {
    }

    public BusinessUnitConfigurationPreference(BusinessUnit businessUnitID, BusinessUnitConfiguration businessUnitConfigID) {
        this.businessUnitID = businessUnitID;
        this.businessUnitConfigID = businessUnitConfigID;
    }

    public String getConfigurationValue() {
        return configurationValue;
    }

    public void setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
    }

    public BusinessUnit getBusinessUnitID() {
        return businessUnitID;
    }

    public void setBusinessUnitID(BusinessUnit businessUnitID) {
        this.businessUnitID = businessUnitID;
    }

    public BusinessUnitConfiguration getBusinessUnitConfigID() {
        return businessUnitConfigID;
    }

    public void setBusinessUnitConfigID(BusinessUnitConfiguration businessUnitConfigID) {
        this.businessUnitConfigID = businessUnitConfigID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessUnitConfigurationPreference)) return false;
        BusinessUnitConfigurationPreference that = (BusinessUnitConfigurationPreference) o;
        return getBusinessUnitID().equals(that.getBusinessUnitID()) &&
                getBusinessUnitConfigID().equals(that.getBusinessUnitConfigID()) &&
                Objects.equals(getConfigurationValue(), that.getConfigurationValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessUnitID(), getBusinessUnitConfigID(), getConfigurationValue());
    }
}
