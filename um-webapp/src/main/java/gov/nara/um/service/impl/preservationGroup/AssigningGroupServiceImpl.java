package gov.nara.um.service.impl.preservationGroup;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.IAssigningGroupDao;
import gov.nara.um.persistence.model.preservationGroup.AssigningGroup;
import gov.nara.um.service.preservationGroup.IAssigningGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class AssigningGroupServiceImpl  extends AbstractLongIdService<AssigningGroup> implements IAssigningGroupService {

    @Autowired
    private IAssigningGroupDao dao;

    public AssigningGroupServiceImpl() {
        super();
    }

    // API

    // find
    @Override
    @Transactional(readOnly = true)
    public AssigningGroup findByName(final String name) {
        return dao.findByName(name);
    }

    // other

    // Spring

    @Override
    protected final IAssigningGroupDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<AssigningGroup> getSpecificationExecutor() {
        return dao;
    }
}
