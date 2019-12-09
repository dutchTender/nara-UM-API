package gov.nara.um.persistence.dao.businessunits;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBusinessUnitConfigurationDao extends JpaRepository<BusinessUnitConfiguration, Long>, JpaSpecificationExecutor<BusinessUnitConfiguration>, IByNameApi<BusinessUnitConfiguration> {


    public BusinessUnitConfiguration findByName(String name);
}
