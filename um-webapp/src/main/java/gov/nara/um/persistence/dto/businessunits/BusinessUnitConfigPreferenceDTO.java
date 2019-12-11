package gov.nara.um.persistence.dto.businessunits;

public class BusinessUnitConfigPreferenceDTO {



    private Long business_unit_config_id;


    private String configuration_value;


    public BusinessUnitConfigPreferenceDTO() {
    }


    public Long getBusiness_unit_config_id() {
        return business_unit_config_id;
    }

    public void setBusiness_unit_config_id(Long business_unit_config_id) {
        this.business_unit_config_id = business_unit_config_id;
    }

    public String getConfiguration_value() {
        return configuration_value;
    }

    public void setConfiguration_value(String configuration_value) {
        this.configuration_value = configuration_value;
    }

}
