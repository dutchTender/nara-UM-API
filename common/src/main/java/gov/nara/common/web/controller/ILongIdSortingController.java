package gov.nara.common.web.controller;

import gov.nara.common.persistence.model.ILongEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ILongIdSortingController<T extends ILongEntity> {

    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    public List<T> findAllPaginated(final int page, final int size);

    public List<T> findAllSorted(final String sortBy, final String sortOrder);

    public List<T> findAll(final HttpServletRequest request);

}
