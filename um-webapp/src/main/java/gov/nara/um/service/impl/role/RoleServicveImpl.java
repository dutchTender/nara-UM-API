package gov.nara.um.service.impl.role;

import gov.nara.common.persistence.service.AbstractLongIdService;
import gov.nara.um.persistence.dao.role.IRoleJpaDao;
import gov.nara.um.persistence.model.role.Role;
import gov.nara.um.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleServicveImpl  extends AbstractLongIdService<Role> implements IRoleService {

    @Autowired
    private IRoleJpaDao dao;

    public RoleServicveImpl() {
    }

    @Override
    public Role findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    protected final IRoleJpaDao getDao() {
        return dao;
    }


    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }
}
