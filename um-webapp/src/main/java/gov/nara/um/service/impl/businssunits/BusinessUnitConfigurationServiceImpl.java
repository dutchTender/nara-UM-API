package gov.nara.um.service.impl.businssunits;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.common.persistence.service.AbstractService;
import gov.nara.um.persistence.dao.businessunits.IBusinessUnitConfigurationDao;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfiguration;

import gov.nara.um.service.bussinessunits.IBusinessUnitConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessUnitConfigurationServiceImpl extends AbstractService<BusinessUnitConfiguration> implements IBusinessUnitConfigurationService {

    @Autowired
    private IBusinessUnitConfigurationDao dao;

    public BusinessUnitConfigurationServiceImpl() { super(); }

    @Override
    public BusinessUnitConfiguration findByName(String name) {
        return dao.findByName(name);
    }


    @Override
    protected final IBusinessUnitConfigurationDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<BusinessUnitConfiguration> getSpecificationExecutor() {
        return dao;
    }



}

