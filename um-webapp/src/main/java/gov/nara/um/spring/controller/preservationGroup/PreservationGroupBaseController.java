package gov.nara.um.spring.controller.preservationGroup;

import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.service.preservationGroup.IAssigningGroupService;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class PreservationGroupBaseController extends AbstractLongIdController<PreservationGroup> {


    @Autowired
    private IPreservationGroupService preservationGroupService;

    @Autowired
    private IAssigningGroupService assigningGroupServiceGroupService;

    @Override
    protected IPreservationGroupService getService() {
        return preservationGroupService;
    }

    protected IAssigningGroupService getAssigningGroupService() {
        return assigningGroupServiceGroupService;
    }
}
