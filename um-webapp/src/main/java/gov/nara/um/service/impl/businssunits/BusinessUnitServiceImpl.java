package gov.nara.um.service.impl.businssunits;

import gov.nara.common.persistence.service.AbstractService;
import gov.nara.um.persistence.dao.businessunits.IBusinessUnitDao;
import gov.nara.um.persistence.model.bussinessUnits.BusinessUnit;
import gov.nara.um.service.bussinessunits.IBusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BusinessUnitServiceImpl extends AbstractService<BusinessUnit> implements IBusinessUnitService {

    @Autowired
    private IBusinessUnitDao dao;




    public BusinessUnitServiceImpl() {
        super();
    }

    // API
    // find

    @Override
    @Transactional(readOnly = true)
    public BusinessUnit findByName(final String name) {
        return dao.findByName(name);
    }

    // other


    // remove user



    // Spring

    @Override
    protected final IBusinessUnitDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<BusinessUnit> getSpecificationExecutor() {
        return dao;
    }
}
