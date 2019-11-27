package gov.nara.um.spring.controller;

import gov.nara.common.web.controller.AbstractController;
import gov.nara.um.persistence.dto.BusinessUnitConfigPreferenceDTO;
import gov.nara.um.persistence.dto.BusinessUnitDTO;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.persistence.model.BusinessUnitConfigurationPreference;
import gov.nara.um.service.IBusinessUnitConfigurationService;
import gov.nara.um.service.IBusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;

abstract class BusinessUnitBaseController  extends AbstractController<BusinessUnit> {

    @Autowired
    private IBusinessUnitConfigurationService configurationService;

    @Autowired
    private IBusinessUnitService service;

    public BusinessUnitDTO buildBusinessUnitDTO(BusinessUnit currentBU){
        BusinessUnitDTO businessUnitDTO = new BusinessUnitDTO();
        businessUnitDTO.setId(currentBU.getId());
        businessUnitDTO.setName(currentBU.getName());
        businessUnitDTO.setOrg_code(currentBU.getOrg_code());
        businessUnitDTO.setLdapName(currentBU.getLdapName());

        return businessUnitDTO;
    }

    public BusinessUnitConfigPreferenceDTO buildBusinessConfigPreferenceDTO(BusinessUnitConfigurationPreference currentBUCP){
        BusinessUnitConfigPreferenceDTO businessUnitConfigPreferenceDTO = new BusinessUnitConfigPreferenceDTO();
        businessUnitConfigPreferenceDTO.setBusiness_unit_id(currentBUCP.getBusinessUnitID().getId());
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
    @Override
    protected final IBusinessUnitService getService() {
        return service;
    }


    protected final IBusinessUnitConfigurationService getConfigurationService() {
        return configurationService;
    }


}
