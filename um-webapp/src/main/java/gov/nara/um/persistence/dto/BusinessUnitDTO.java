package gov.nara.um.persistence.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "id", "name", "org_code", "ldapName", "businessUnitConfigPreferences" })
public class BusinessUnitDTO {



    private Integer Id;
    private String name;
    private String org_code;
    private String ldapName;

    private List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferences = new ArrayList<>();

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setBusinessUnitConfigPreferences(List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferencesDTOs) {
        this.businessUnitConfigPreferences = businessUnitConfigPreferencesDTOs;
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

    public String getLdapName() {
        return ldapName;
    }

    public void setLdapName(String ldapName) {
        this.ldapName = ldapName;
    }

    public List<BusinessUnitConfigPreferenceDTO> getBusinessUnitConfigPreferences() {
        return businessUnitConfigPreferences;
    }

    public BusinessUnitConfigPreferenceDTO addBusinessUnitConfigPreferenceDTO(BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){
        this.businessUnitConfigPreferences.add(businessUnitConfigPreferenceDTO);
        return  businessUnitConfigPreferenceDTO;
    }

}
