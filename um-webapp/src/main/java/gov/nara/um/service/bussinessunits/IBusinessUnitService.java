package gov.nara.um.service.bussinessunits;

import gov.nara.common.persistence.service.IService;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;

public interface IBusinessUnitService extends IService<BusinessUnit> {


             public BusinessUnit findByCode(String Code);

}
