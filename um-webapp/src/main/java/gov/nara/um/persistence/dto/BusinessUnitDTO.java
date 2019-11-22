package gov.nara.um.persistence.dto;

import gov.nara.um.persistence.model.BusinessUnitConfigurationPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusinessUnitDTO {

    private Integer id;

    private String name;

    private String org_code;

    private String ldap_name;


    private List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferences = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<BusinessUnitConfigPreferenceDTO> getBusinessUnitConfigPreferences() {
        return businessUnitConfigPreferences;
    }

    public void setBusinessUnitConfigPreferences(List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferences) {
        this.businessUnitConfigPreferences = businessUnitConfigPreferences;
    }

    public BusinessUnitConfigPreferenceDTO addBusinessUnitConfigPreference(BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){
        businessUnitConfigPreferences.add(businessUnitConfigPreferenceDTO);

        return businessUnitConfigPreferenceDTO;
    }

    public void removeBusinessUnitConfigPreference(BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){

        for(Iterator<BusinessUnitConfigPreferenceDTO> iterBUCP = businessUnitConfigPreferences.iterator(); iterBUCP.hasNext(); ) {
            BusinessUnitConfigPreferenceDTO current = iterBUCP.next();
            if(current.equals(businessUnitConfigPreferenceDTO)){
                iterBUCP.remove();
            }
        }
    }



}
