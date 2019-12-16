package gov.nara.um.persistence.dao.role;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.role.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {

    public Role findByName(String name);

}
