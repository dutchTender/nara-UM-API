package gov.nara.um.persistence.dao.businessunits;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBusinessUnitDao extends JpaRepository<BusinessUnit, Integer>, JpaSpecificationExecutor<BusinessUnit>, IByNameApi<BusinessUnit> {

            public BusinessUnit findByName(String name);
            public BusinessUnit findByCode(String code);
}
