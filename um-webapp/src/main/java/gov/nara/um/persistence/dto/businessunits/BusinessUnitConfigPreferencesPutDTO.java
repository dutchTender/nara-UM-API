package gov.nara.um.persistence.dto.businessunits;

import java.util.ArrayList;
import java.util.List;

public class BusinessUnitConfigPreferencesPutDTO {

    private List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferences = new ArrayList<>();

    public List<BusinessUnitConfigPreferenceDTO> getBusinessUnitConfigPreferences() {
        return businessUnitConfigPreferences;
    }

    public void setBusinessUnitConfigPreferences(List<BusinessUnitConfigPreferenceDTO> businessUnitConfigPreferences) {
        this.businessUnitConfigPreferences = businessUnitConfigPreferences;
    }

    public void addBusinessUnitConfigPreference(BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){

        businessUnitConfigPreferences.add(businessUnitConfigPreferenceDTO);
    }


}
