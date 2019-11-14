package gov.nara.um.persistence.dao;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBusinessUnitDao extends JpaRepository<BusinessUnit, Long>, JpaSpecificationExecutor<BusinessUnit>, IByNameApi<BusinessUnit> {


}
