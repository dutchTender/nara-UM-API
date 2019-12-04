package gov.nara.um.spring.web.preservationGroup;

import gov.nara.common.web.controller.AbstractLongIdController;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.service.preservationGroup.IPreservationGroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class PreservationGroupBaseController extends AbstractLongIdController<PreservationGroup> {


    @Autowired
    private IPreservationGroupService preservationGroupService;



    @Override
    protected IPreservationGroupService getService() {
        return preservationGroupService;
    }


}
