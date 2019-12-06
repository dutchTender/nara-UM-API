package gov.nara.um.service.impl.preservationGroup;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.IPreservationGroupDao;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PreservationGroupServiceImpl extends AbstractLongIdService<PreservationGroup> implements IPreservationGroupService {



    @Autowired
    private IPreservationGroupDao  dao;

    public PreservationGroupServiceImpl() {
        super();
    }

    // API

    // find
    @Override
    @Transactional(readOnly = true)
    public PreservationGroup findByName(final String name) {
        return dao.findByName(name);
    }

    // other

    // Spring

    @Override
    protected final IPreservationGroupDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<PreservationGroup> getSpecificationExecutor() {
        return dao;
    }
}
