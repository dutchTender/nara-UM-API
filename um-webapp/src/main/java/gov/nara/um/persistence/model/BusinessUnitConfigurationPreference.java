package gov.nara.um.persistence.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
//@Table(name = "business_unit_config_values", schema = "oif_ods")
@Table(name = "business_unit_config_values")
public class BusinessUnitConfigurationPreference {

    @EmbeddedId
    private BusinessUnitConfigurationID businessUnitPreferenceID;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("businessUnitID")
    @JoinColumn(name="business_unit_catalog_id", nullable=false)
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("businessUnitConfigID")
    @JoinColumn(name="business_unit_config_id", nullable=false)
    private BusinessUnitConfiguration businessUnitConfiguration;


    @Column(name = "configuration_value")
    private String configurationValue;


    public BusinessUnitConfigurationPreference(BusinessUnit businessUnit, BusinessUnitConfiguration businessUnitConfiguration) {
        this.businessUnit = businessUnit;
        this.businessUnitConfiguration = businessUnitConfiguration;
    }

    public BusinessUnitConfigurationID getBusinessUnitPreferenceID() {
        return businessUnitPreferenceID;
    }

    public void setBusinessUnitPreferenceID(BusinessUnitConfigurationID businessUnitPreferenceID) {
        this.businessUnitPreferenceID = businessUnitPreferenceID;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public BusinessUnitConfiguration getBusinessUnitConfiguration() {
        return businessUnitConfiguration;
    }

    public void setBusinessUnitConfiguration(BusinessUnitConfiguration businessUnitConfiguration) {
        this.businessUnitConfiguration = businessUnitConfiguration;
    }

    public String getConfigurationValue() {
        return configurationValue;
    }

    public void setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessUnitConfigurationPreference)) return false;
        BusinessUnitConfigurationPreference that = (BusinessUnitConfigurationPreference) o;
        return Objects.equals(getBusinessUnitPreferenceID(), that.getBusinessUnitPreferenceID()) &&
                getBusinessUnit().equals(that.getBusinessUnit()) &&
                getBusinessUnitConfiguration().equals(that.getBusinessUnitConfiguration()) &&
                Objects.equals(getConfigurationValue(), that.getConfigurationValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessUnitPreferenceID(), getBusinessUnit(), getBusinessUnitConfiguration(), getConfigurationValue());
    }
}
