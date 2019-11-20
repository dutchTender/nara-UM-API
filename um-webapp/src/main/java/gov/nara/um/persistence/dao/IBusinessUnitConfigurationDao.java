package gov.nara.um.persistence.dao;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.BussinessUnitConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBusinessUnitConfigurationDao extends JpaRepository<BussinessUnitConfiguration, Long>, JpaSpecificationExecutor<BussinessUnitConfiguration>, IByNameApi<BussinessUnitConfiguration> {


    public BussinessUnitConfiguration  findByName(String name);
}
