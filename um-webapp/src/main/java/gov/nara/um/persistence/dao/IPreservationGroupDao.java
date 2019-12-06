package gov.nara.um.persistence.dao;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPreservationGroupDao  extends JpaRepository<PreservationGroup, Long>, JpaSpecificationExecutor<PreservationGroup>, IByNameApi<PreservationGroup> {

            public PreservationGroup findByName(String name);

}
