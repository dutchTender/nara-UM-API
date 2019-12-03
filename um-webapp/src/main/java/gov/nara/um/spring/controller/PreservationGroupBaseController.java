package gov.nara.um.spring.controller;

import gov.nara.common.persistence.service.ILongRawService;
import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.dao.IPreservationGroupDao;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.service.IPreservationGroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class PreservationGroupBaseController extends AbstractLongIdController<PreservationGroup> {


    @Autowired
    private IPreservationGroupService preservationGroupService;

    @Override
    protected ILongRawService<PreservationGroup> getService() {
        return preservationGroupService;
    }
}
