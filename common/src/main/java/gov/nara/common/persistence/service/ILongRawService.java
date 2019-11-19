package gov.nara.common.persistence.service;

import gov.nara.common.interfaces.ILongOperations;
import gov.nara.common.interfaces.IWithName;
import org.springframework.data.domain.Page;

public interface ILongRawService<T extends IWithName> extends ILongOperations<T> {

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);
    Page<T> findAllPaginatedRaw(final int page, final int size);

}
