package gov.nara.common.web.controller;

import gov.nara.common.persistence.model.INameableEntity;
import gov.nara.common.web.RestPreconditions;

public abstract class AbstractController<T extends INameableEntity> extends AbstractReadOnlyController<T> {
    
    // save/create/persist

    protected final void createInternal(final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        getService().create(resource);
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final Integer id, final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkRequestState(resource.getId() == id);
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        getService().update(resource);
    }

    // delete/remove

    protected final void deleteByIdInternal(final Integer id) {
        getService().delete(id);
    }

}
