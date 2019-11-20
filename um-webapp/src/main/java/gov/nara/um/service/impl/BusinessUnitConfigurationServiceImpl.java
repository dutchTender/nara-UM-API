package gov.nara.um.service.impl;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.IBusinessUnitConfigurationDao;
import gov.nara.um.persistence.model.BussinessUnitConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public class BusinessUnitConfigurationServiceImpl extends AbstractLongIdService<BussinessUnitConfiguration>  {

    @Autowired
    private IBusinessUnitConfigurationDao dao;

    public BusinessUnitConfigurationServiceImpl() { super(); }

    @Override
    public BussinessUnitConfiguration findByName(String name) {
        return dao.findByName(name);
    }


    @Override
    protected final IBusinessUnitConfigurationDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<BussinessUnitConfiguration> getSpecificationExecutor() {
        return dao;
    }
}

