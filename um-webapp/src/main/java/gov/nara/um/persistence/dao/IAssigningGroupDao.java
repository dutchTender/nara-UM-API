package gov.nara.um.persistence.dao;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.preservationGroup.AssigningGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAssigningGroupDao extends JpaRepository<AssigningGroup, Long>, JpaSpecificationExecutor<AssigningGroup>, IByNameApi<AssigningGroup> {
}
