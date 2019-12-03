package gov.nara.um.spring.controller;

import gov.nara.common.web.controller.ILongIdSortingController;
import gov.nara.um.persistence.model.preservationGroup.PreservationGroup;
import gov.nara.um.util.UmMappings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = UmMappings.PERSERVATIONGROUPS)
public class PreservationGroupController extends  PreservationGroupBaseController  implements ILongIdSortingController<PreservationGroup> {


    @Override
    public List<PreservationGroup> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public List<PreservationGroup> findAllPaginated(int page, int size) {
        return null;
    }

    @Override
    public List<PreservationGroup> findAllSorted(String sortBy, String sortOrder) {
        return null;
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Override
    public List<PreservationGroup> findAll(HttpServletRequest request) {
        return findAllInternal(request);
    }
}
