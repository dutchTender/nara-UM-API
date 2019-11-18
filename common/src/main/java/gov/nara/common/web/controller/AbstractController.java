package gov.nara.common.web.controller;

import gov.nara.common.persistence.model.INameableEntity;
import gov.nara.common.web.RestPreconditions;

public abstract class AbstractController<T extends INameableEntity> extends AbstractReadOnlyController<T> {
    
    // save/create/persist

    protected final void createInternal(final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        // verifies that id is not part of the payload
        RestPreconditions.checkRequestState(resource.getId() == null);
        getService().create(resource);
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final Integer id, final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(id);
        /////////////////////////////////////////////////////////////////////////////////////////////
        // the resource pay load will never have the id for a put call
        // the id is passed though via parameters and hooked into the id parameter for this function
        // check if payload has an id.
        RestPreconditions.checkRequestState(resource.getId() == null);
        ////////////////////////////////////////////////////////////////////////////////////////////
        RestPreconditions.checkNotNull(getService().findOne(id));
        getService().update(resource);
    }

    // delete/remove

    protected final void deleteByIdInternal(final Integer id) {
        getService().delete(id);
    }

}
