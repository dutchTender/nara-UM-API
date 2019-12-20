package gov.nara.um.persistence.dto.businessunits;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "business_unit_id", "business_unit_name", "org_code", "ldap_name", "business_unit_Preferences" })
public class BusinessUnitDTO {



    private Integer business_unit_id;

    private String business_unit_name;

    private String org_code;

    private String ldap_name;

    private List<BusinessUnitConfigPreferenceDTO> business_unit_Preferences = new ArrayList<>();


    public Integer getBusiness_unit_id() {
        return business_unit_id;
    }

    public void setBusiness_unit_id(Integer business_unit_id) {
        this.business_unit_id = business_unit_id;
    }

    public String getBusiness_unit_name() {
        return business_unit_name;
    }

    public void setBusiness_unit_name(String business_unit_name) {
        this.business_unit_name = business_unit_name;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getLdap_name() {
        return ldap_name;
    }

    public void setLdap_name(String ldap_name) {
        this.ldap_name = ldap_name;
    }

    public List<BusinessUnitConfigPreferenceDTO> getBusiness_unit_Preferences() {
        return business_unit_Preferences;
    }

    public void setBusiness_unit_Preferences(List<BusinessUnitConfigPreferenceDTO> business_unit_Preferences) {
        this.business_unit_Preferences = business_unit_Preferences;
    }

    public BusinessUnitConfigPreferenceDTO addBusinessUnitConfigPreferenceDTO(BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){
        this.business_unit_Preferences.add(businessUnitConfigPreferenceDTO);
        return  businessUnitConfigPreferenceDTO;
    }

}
