package gov.nara.um.service.impl;

import gov.nara.common.persistence.service.AbstractLongIdService;

import gov.nara.um.persistence.dao.IUserJpaDao;
import gov.nara.um.persistence.model.User;
import gov.nara.um.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractLongIdService<User>  implements IUserService {

    @Autowired
    private IUserJpaDao dao;

    public UserServiceImpl() {
        super();
    }

    // API

    // find

    @Override
    public User findOne(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(final String name) {
        return dao.findByName(name);
    }

    // other

    // Spring

    @Override
    protected final IUserJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<User> getSpecificationExecutor() {
        return dao;
    }
}