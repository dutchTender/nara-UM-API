package gov.nara.common.persistence.service;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.common.interfaces.IWithName;

public interface ILongIdService<T extends IWithName> extends ILongRawService<T>, IByNameApi<T> {

    //

}
