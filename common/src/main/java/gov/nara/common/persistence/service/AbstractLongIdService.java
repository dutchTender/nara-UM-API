package gov.nara.common.persistence.service;

import gov.nara.common.persistence.model.ILongNameableEntity;


public abstract class AbstractLongIdService<T extends ILongNameableEntity> extends AbstractLongIdRawService<T> implements ILongIdService<T> {

    public AbstractLongIdService() {
        super();
    }

    // API

}

