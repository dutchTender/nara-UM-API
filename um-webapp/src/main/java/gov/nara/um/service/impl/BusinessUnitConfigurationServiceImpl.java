package gov.nara.um.service.impl;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.IBusinessUnitConfigurationDao;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnitConfiguration;

import gov.nara.um.service.IBusinessUnitConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessUnitConfigurationServiceImpl extends AbstractLongIdService<BusinessUnitConfiguration> implements IBusinessUnitConfigurationService {

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

