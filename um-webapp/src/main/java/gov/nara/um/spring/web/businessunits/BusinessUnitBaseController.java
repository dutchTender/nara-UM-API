package gov.nara.um.spring.web.businessunits;

import gov.nara.common.web.controller.AbstractController;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfigurationPreference;
import gov.nara.um.service.bussinessunits.IBusinessUnitConfigurationService;
import gov.nara.um.service.bussinessunits.IBusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class BusinessUnitBaseController  extends AbstractController<BusinessUnit> {

    @Autowired
    private IBusinessUnitConfigurationService configurationService;

    @Autowired
    private IBusinessUnitService service;

    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setBusiness_unit_id(currentBU.getId());
        businessUnitDTO.setBusiness_unit_name(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getOrg_code());
        businessUnitDTO.setLdap_name(currentBU.getLdapName());

        for(Iterator<BusinessUnitConfigurationPreference> iterBUCP = currentBU.getBusinessUnitConfigurationPreferences().listIterator(); iterBUCP.hasNext();){
            BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = buildBusinessConfigPreferenceDTO(iterBUCP.next());
            businessUnitDTO.addBusinessUnitConfigPreferenceDTO(businessUnitConfigPreferenceDTO);
        }

        return businessUnitDTO;
    }

    public BusinessUnitConfigPreferenceDTO buildBusinessConfigPreferenceDTO(BusinessUnitConfigurationPreference currentBUCP){
        BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = new BusinessUnitConfigPreferenceDTO();
        //businessUnitConfigPreferenceDTO.setBusiness_unit_id(currentBUCP.getBusinessUnitID().getId());
        businessUnitConfigPreferenceDTO.setBusiness_unit_config_id(currentBUCP.getBusinessUnitConfigID().getId());
        businessUnitConfigPreferenceDTO.setConfiguration_value(currentBUCP.getConfigurationValue());
        return businessUnitConfigPreferenceDTO;
    }

    public BusinessUnitConfigurationPreference buildBusinessUnitConfigurationPreference(BusinessUnit currentBU, BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO){

        BusinessUnitConfigurationPreference businessUnitConfigurationPreference = new BusinessUnitConfigurationPreference();
        businessUnitConfigurationPreference.setBusinessUnitID(currentBU);
        businessUnitConfigurationPreference.setBusinessUnitConfigID(configurationService.findOne(businessUnitConfigPreferenceDTO.getBusiness_unit_config_id()));
        businessUnitConfigurationPreference.setConfigurationValue(businessUnitConfigPreferenceDTO.getConfiguration_value());

        return businessUnitConfigurationPreference;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Spring
    // dependency injection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BusinessUnitDTO> buildBuindessUnitDTOList(List<BusinessUnit> businessUnitList){
        List<BusinessUnitDTO> returnList = new ArrayList<>();

        for(Iterator<BusinessUnit> iterBU = businessUnitList.listIterator(); iterBU.hasNext(); ) {
            BusinessUnit currentBU = iterBU.next();
            BusinessUnitDTO businessUnitDTO = buildBusinessUnitDTO(currentBU);

            returnList.add(businessUnitDTO);

        }

        return returnList;

    }


    @Override
    protected final IBusinessUnitService getService() {
        return service;
    }


    protected final IBusinessUnitConfigurationService getConfigurationService() {
        return configurationService;
    }


}
