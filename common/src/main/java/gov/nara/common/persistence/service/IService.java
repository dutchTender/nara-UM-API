package gov.nara.common.persistence.service;

import gov.nara.common.interfaces.IByNameApi;
import gov.nara.common.interfaces.IWithName;

public interface IService<T extends IWithName> extends IRawService<T>, IByNameApi<T> {

    //

}
