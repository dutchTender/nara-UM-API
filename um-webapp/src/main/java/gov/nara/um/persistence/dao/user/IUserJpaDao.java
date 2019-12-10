package gov.nara.um.persistence.dao.user;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserJpaDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, IByNameApi<User> {
    //
    public User findByName(String name);

}