package gov.nara.um.persistence.dto.businessunits;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "id", "name", "org_code", "ldapName", "businessUnitConfigPreferences" })
public class BusinessUnitDTO {



    private Integer Id;

    private String name;

    private String org_code;

    private String ldap_name;

    private List<BusinessUnitConfigPreferenceDTO> business_unit_Preferences = new ArrayList<>();

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
