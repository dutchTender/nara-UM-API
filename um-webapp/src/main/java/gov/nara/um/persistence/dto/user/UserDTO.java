package gov.nara.um.persistence.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gov.nara.um.persistence.dto.businessunits.BusinessUnitDTO;
import gov.nara.um.persistence.dto.preservationgroups.PreservationGroupDTO;
import gov.nara.um.persistence.dto.role.RoleDTO;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({ "user_id", "user_name", "user_type", "user_roles", "business_units", "preservation_groups" })
public class UserDTO {

    private Long user_id;

    private String user_name;

    private String user_type;

    private List<BusinessUnitDTO> business_units = new ArrayList<>();
    public void addBusinssUnitDTO(BusinessUnitDTO businessUnitDTO){ business_units.add(businessUnitDTO);}

    private List<PreservationGroupDTO> preservation_groups = new ArrayList<>();
    public void addPreservationGroupDTO(PreservationGroupDTO preservationGroupDTO){
        preservation_groups.add(preservationGroupDTO);
    }

    private List<RoleDTO> use_roles = new ArrayList<>();
    public void addRoleDTO(RoleDTO roleDTO){
        use_roles.add(roleDTO);
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public List<BusinessUnitDTO> getBusiness_units() {
        return business_units;
    }

    public void setBusiness_units(List<BusinessUnitDTO> business_units) {
        this.business_units = business_units;
    }

    public List<PreservationGroupDTO> getPreservation_groups() {
        return preservation_groups;
    }

    public void setPreservation_groups(List<PreservationGroupDTO> preservation_groups) {
        this.preservation_groups = preservation_groups;
    }

    public List<RoleDTO> getUse_roles() {
        return use_roles;
    }

    public void setUse_roles(List<RoleDTO> use_roles) {
        this.use_roles = use_roles;
    }
}
